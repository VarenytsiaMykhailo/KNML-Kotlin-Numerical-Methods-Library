package com.github.varenytsiamykhailo.knml.bignumbers

import kotlin.math.cos
import kotlin.math.sin
import org.apache.commons.math3.complex.Complex
import kotlin.math.PI

class FFT(var n: Int) {
    fun fft(vec: List<Complex>): List<Complex> {
        val n = vec.size
        var k = 0
        while ((1 shl k) < n) k++
        val rev = IntArray(n)
        rev[0] = 0
        var high1 = -1

        for (i in 1 until n) {
            val flag = i and (i - 1)
            if (flag == 0)
                high1++
            rev[i] = rev[i xor (1 shl high1)]
            rev[i] = rev[i] or (1 shl (k - high1 - 1))
        }

        val roots = List(n) { i ->
            val alpha = 2 * PI * i / n
            Complex(cos(alpha), sin(alpha))
        }

        val cur = MutableList(n) { i -> vec[rev[i]] }

        var len = 1
        while (len < n) {
            val ncur = MutableList(n) { Complex(0.0, 0.0) }
            val rstep = roots.size / (len * 2)
            var pdest = 0

            while (pdest < n) {
                var p1 = pdest

                for (i in 0 until len) {
                    val val1 = roots[i * rstep].multiply(cur[p1 + len])
                    ncur[pdest] = cur[p1].add(val1)
                    ncur[pdest + len] = cur[p1].subtract(val1)
                    pdest++
                    p1++
                }
                pdest += len
            }
            cur.clear()
            cur.addAll(ncur)
            len = len shl 1
        }

        return cur.toList()
    }

    fun fftRev(vec: List<Complex>): List<Complex> {
        var res = fft(vec)

        res = res.map { Complex(it.real / vec.size, it.imaginary / vec.size) }
        res = res.reversed().toMutableList()
        res.add(0, res.removeAt(res.size - 1))

        return res
    }
}
