package com.github.varenytsiamykhailo.knml.bignumbers

import com.github.varenytsiamykhailo.knml.util.Matrix
import org.apache.commons.math3.complex.Complex
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode
import kotlin.math.pow
import kotlin.math.round

class MultiplyMethod {
    fun karatsuba(a: BigNumber, b: BigNumber): BigNumber {
        var x = a
        var y = b
        var wasMinus = false
        if (x.haveMinus() || y.haveMinus()) {
            if (x.haveMinus() && y.haveMinus()) {
                x = BigNumber(x.number.drop(1))
                y = BigNumber(y.number.drop(1))
            } else {
                wasMinus = true
                if (x.haveMinus() && !y.haveMinus()) {
                    x = BigNumber(x.number.drop(1))
                }
                if (y.haveMinus()) {
                    y = BigNumber(y.number.drop(1))
                }
            }
        }

        if (x.number.length <= 1 || y.number.length <= 1) {
            var res = x.mul(y)
            if (wasMinus) {
                res = BigNumber("-" + (res).number)
            }
            return res
        }

        val n = kotlin.math.max(x.number.length, y.number.length)
        val m = (n+1) / 2

        val p1 = x.getParts(2, m)

        val a1 = p1[1]
        val a0 = p1[0]

        val p2 = y.getParts(2, m)
        val b1 = p2[1]
        val b0 = p2[0]

        val z2 = karatsuba(a1, b1)
        val z1 = karatsuba(a1 + a0, b1 + b0)
        val z0 = karatsuba(a0, b0)

        var res = z2.multiplyByPower(2 * m) + (z1 - z2 - z0).multiplyByPower(m) + z0

        res = res.dropFirstZeros()

        if (wasMinus) {
            res = BigNumber("-" + res.number)
        }

        return res
    }

    fun schonhageShtrassen(x: BigNumber, y: BigNumber): BigNumber {
        var m = x.number.length + y.number.length - 1

        if (!(Integer.bitCount(m) == 1 && m > 0)) {
            val highestBit = Integer.highestOneBit(m)
            m = highestBit shl 1
        }

        val x_coeff = koeffs(x.number, m)
        val y_coeff = koeffs(y.number, m)

        val fft = FFT(m)
        val aVals = fft.fft(x_coeff)
        val bVals = fft.fft(y_coeff)
        var c_vals = MutableList(aVals.size) { Complex(0.0, 0.0) }

        for (i in 0 until aVals.size) {
            c_vals[i] = aVals[i].multiply(bVals[i])
        }

        val c = fft.fftRev(c_vals).toMutableList();
        var result = ""

        for (i in 0 until  c.size - 1) {
            c[i] = Complex(round(c[i].real), c[i].imaginary)

            if (c[i].real > 9.0) {
                val ost = c[i].real.toInt() / 10
                c[i] = Complex((c[i].real % 10), c[i].imaginary)

                if (i == c.size - 2) {
                    result = c[i].real.toInt().toString() + result
                    c.add(Complex(1 + ost.toDouble(), 0.0))
                    result = c[i+1].real.toInt().toString() + result

                    continue
                }

                c[i + 1] = Complex((c[i + 1].real + ost), c[i + 1].imaginary)
            }

            result = c[i].real.toInt().toString() + result
        }

        return BigNumber(result).dropFirstZeros()
    }

    private fun koeffs(y: String, m: Int): List<Complex> {
        val n = y.length
        var result = MutableList(m) { Complex(0.0, 0.0) }

        for (i in n - 1 downTo 0) {
            result[n-i-1] = Complex(y.drop(i).take(1).toDouble(), 0.0)
        }

        return result
    }

    fun toomCook3WayMultiplication(u: BigNumber, v: BigNumber): BigNumber {
        val n = maxOf(u.number.length, v.number.length)

        // Количество разбиений
        val r = 3
        val m = (n + r - 1) / r

        val uPoly = u.getParts(r, m)
        val vPoly = v.getParts(r, m)

        val doublePoints = getArrayDouble(doubleArrayOf(0.0, 1.0, -1.0, -2.0, 100.0))
        val points = arrayOf(0, 1, -1, -2, 100)
        val matrix = getMatrix(points, uPoly.size)

        val uPointValuePairs = evaluatePoints(matrix, uPoly, points)
        val vPointValuePairs = evaluatePoints(matrix, vPoly, points)

        var rPointValuePairs = multiplyPolyPointValue(uPointValuePairs, vPointValuePairs)

        val vandermondeMatrix = getVandermondeMatrix(doublePoints, rPointValuePairs.size, withInfinity=true)
        val resultMatrix: Matrix? = vandermondeMatrix.invertible()

        if (resultMatrix != null) {
            val res = solveEquation(resultMatrix, rPointValuePairs, points)
            var answer = res.getValue(points[res.size-1])

            for (i in res.size-2 downTo 0 ) {
                answer = answer.multiplyByPower(m)
                answer += res.getValue(points[i])
            }

            return answer.dropFirstZeros()
        }

        return BigNumber("0")
    }

    private fun evaluatePoints(matrix: Array<Array<BigNumber>>, poly: MutableList<BigNumber>, points: Array<Int>): HashMap<Int, BigNumber> {
        val n = matrix.size
        val m = poly.size
        val result = HashMap<Int, BigNumber>()

        for (i in 0 until n) {
            var value = BigNumber("0")

            for (j in 0 until m) {
                value += karatsuba(matrix[i][j], poly[j])
            }
            result[points[i]] = value
        }

        return result
    }

    private fun getArrayDouble(points: DoubleArray): Array<Double> {
        var result = Array(points.size){0.0}

        for (i in points.indices) {
            result[i] = points[i]
        }

        return result
    }

    private fun solveEquation(matrix: Matrix, poly: HashMap<Int, BigNumber>, points: Array<Int>): HashMap<Int, BigNumber> {
        val n = matrix.getN()
        val m = poly.size
        val result = HashMap<Int, BigNumber>()

        for (i in 0 until n) {
            var value = BigDecimal.valueOf(0)

            for (j in 0 until m) {
                val bigDecimal = BigInteger(poly.getValue(points[j]).number).toBigDecimal()

                value += BigDecimal.valueOf(matrix.getElem(i, j)) * bigDecimal
                value = value.setScale(4, RoundingMode.HALF_DOWN)
            }

            val v = value.setScale(0, RoundingMode.HALF_DOWN).toBigInteger()
            result[points[i]] = BigNumber(v.toString())
        }
        return result
    }

    private fun multiplyPolyPointValue(a: HashMap<Int, BigNumber>, b: HashMap<Int, BigNumber>): HashMap<Int, BigNumber> {
        val result = HashMap<Int, BigNumber>()

        for (i in a) {
            if (b.containsKey(i.key)) {
                result[i.key] = karatsuba(i.value, b.getValue(i.key))
                b.remove(i.key)

                continue
            }

            result[i.key] = i.value
        }

        for (j in b) {
            result[j.key] = j.value
        }

        return result
    }

    private fun getMatrix(points: Array<Int>, m: Int): Array<Array<BigNumber>> {
        val n = points.size
        val result = Array(n) {Array(m) { BigNumber("0") } }

        for (i in 0 until n) {
            var value = 1

            for (j in 0 until m) {
                if (i == n-1) {
                    if (j == m-1) {
                        result[i][j] = BigNumber("1")

                        continue
                    }
                    result[i][j] =  BigNumber("0")

                    continue
                }

                if (j == 0) {
                    result[i][0] = BigNumber("1")

                    continue
                }

                value *= points[i]
                result[i][j] = BigNumber(value.toString())
            }
        }

        return result
    }

    private fun getVandermondeMatrix(points: Array<Double>, m: Int, withInfinity: Boolean): Matrix {
        val n = points.size
        val result = Matrix(n, m)

        for (i in 0 until n) {

            for (j in 0 until m) {

                if (withInfinity && i == n-1) {

                    if (j == m-1) {
                        result.setElem(i, j,1.0)

                        continue
                    }
                    result.setElem(i, j,0.0)

                    continue
                }

                val value = points[i].pow(j)
                result.setElem(i, j, value)
            }
        }

        return result
    }
}
