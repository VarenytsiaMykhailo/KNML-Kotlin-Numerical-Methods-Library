package com.github.varenytsiamykhailo.knml.util

import java.io.Serializable
import java.lang.Integer.max
import kotlin.math.abs


/**
 * Matrix implementation.
 *
 * This class implements 'matrix' (table) of the size ofg [n] x [m] with its elements.
 *
 * @property [n] is the count of rows (first dimension) of the matrix, starting from 1.
 * @property [m] is the count of columns (second dimension) of the matrix, starting from 1.
 * @constructor This constructor sets default [Double] type zero values *0.0* to each element of the matrix.
 *
 * [n] is the count of rows (first dimension) of the matrix, starting from 1.
 * [m] is the count of columns (second dimension) of the matrix, starting from 1.
 *
 * Use [setElem] or [setElems] methods to set values after creating instance by this constructor.
 *
 * @see Vector
 */
class Matrix constructor(
    private var n: Int,
    private var m: Int
) : Serializable { // getter methods for the 'n', 'm' fields defined below

    private var elems: Array<Array<Double>> =
        emptyArray() // getter and setter methods for the 'elems' field defined below

    init {
        this.elems = Array(n) { Array(m) { 0.0 } }
    }

    /**
     *
     * This constructor initializes the matrix by the input [Array]<[Array]<[Double]>> elems.
     *
     * Use [setElem] or [setElems] methods to set values after creating instance by this constructor.
     */
    constructor(elems: Array<Array<Double>>) : this(elems.size, elems[0].size) {
        this.elems = elems
    }

    /**
     * Returns elem (coordinate).
     *
     * If you need all elems (coordinates) of the matrix, you can use [getElems] method.
     *
     * @param [n] is the count of rows (first dimension) of the matrix, starting from 1.
     * @param [m] is the count of columns (second dimension) of the matrix, starting from 1.
     *
     * @return This method returns elem (coordinate) of [Double] type value by [n] and [m] - position of the elem in the matrix.
     */
    fun getElem(n: Int, m: Int): Double = elems[n][m]

    /**
     * Sets elem (coordinate).
     *
     * If you need to set all elems (coordinates) of the matrix, you can use [setElems] method.
     *
     * This method sets elem (coordinate) of [Double] type value by [n] and [m] - position of the elem in the matrix.
     * @param [n] is the count of rows (first dimension) of the matrix, starting from 1.
     * @param [m] is the count of columns (second dimension) of the matrix, starting from 1.
     */
    fun setElem(n: Int, m: Int, elem: Double) {
        elems[n][m] = elem
    }

    /**
     * Returns [Array]<[Array]<[Double]>> elems (coordinates) of the matrix.
     *
     * If you need only one elem (coordinate) by it's position in the matrix, you can use [getElem] method.
     *
     * @return This method returns elems (coordinates) of [Array]<[Array]<[Double]>> type value of the matrix.
     */
    fun getElems(): Array<Array<Double>> = this.elems

    /**
     * Sets [Array]<[Array]<[Double]>> elems (coordinates) of the matrix.
     *
     * If you need to set only one elem (coordinate) by it's position in the matrix, you can use [setElem] method.
     *
     * @param [elems] This method sets elems (coordinates) of [Array]<[Array]<[Double]>> type value of the matrix.
     */
    fun setElems(elems: Array<Array<Double>>) {
        this.elems = elems
        this.n = elems.size
        this.m = elems[0].size
    }

    /**
     * Returns [n] - the count of rows (first dimension) of the matrix, starting from 1.
     *
     * @return This method returns [n] of [Int] type value.
     *
     * [n] is the count of rows (first dimension) of the matrix, starting from 1.
     */
    fun getN() = this.n

    /**
     * Returns [m] - the count of columns (second dimension) of the matrix, starting from 1.
     *
     * @return This method returns [m] of [Int] type value.
     *
     * [m] is the count of columns (second dimension) of the matrix, starting from 1.
     */
    fun getM() = this.m

    /**
     * Returns `true` if the matrix is square, meaning it has the same number of [rows][.n] and [columns][.m].
     * @return `true` if this matrix is square, `false` if it is rectangular.
     */
    fun isSquare(): Boolean {
        return this.n == this.m
    }

    /**
     * Checks to see if the given input is approximately symmetric. Rounding
     * errors may cause the computation of a matrix to come out non symmetric,
     * where |a[i,h] - a[j, i]| &lt; eps. Despite these errors, it may be
     * preferred to treat the matrix as perfectly symmetric regardless.
     *
     * @param matrix the input matrix
     * @param eps the maximum tolerable difference between two entries
     * @return `true` if the matrix is approximately symmetric
     */
    fun isSymmetric(matrix: Matrix, eps: Double): Boolean {
        if (!matrix.isSquare()) {
            return false
        }
        for (i in 0 until matrix.getN()) {
            for (j in i + 1 until matrix.getM()) {
                if (abs(matrix.getElem(i, j) - matrix.getElem(j, i)) > eps) {
                    return false
                }
            }
        }
        return true
    }

    /**
     * Matrix multiplication.
     *
     * This method implements matrix multiplication of current matrix and input matrix of the [Matrix] type.
     *
     * @param [matrix] the input matrix.
     *
     * @return the result of the multiplication of two matrices which is represented as new [Matrix] output type.
     *
     * Asymptotic complexity: O(n * m)
     */
    @Throws(java.lang.Exception::class)
    fun multiply(matrix: Matrix): Matrix {
        require(this.getM() == matrix.getN() || this.getN() == matrix.getM()) { "The size of 'matrix1.m' does not match to size of 'matrix2.n'" }

        val matrixElemsResult: Array<Array<Double>> =
            Array(this.getN()) { Array(matrix.getM()) { 0.0 } }

        // index 'i' matches to this matrix row and result matrix row
        for (i in 0 until this.getN()) {
            // index 'j' matches to 'matrix' column and result matrix column
            for (j in 0 until matrix.getM()) {
                // index 'k' matches to this matrix column and 'matrix' row
                for (k in 0 until this.getM()) {
                    matrixElemsResult[i][j] += this.getElem(i, k) * matrix.getElem(k, j)
                }
            }
        }

        return Matrix(matrixElemsResult)
    }

    /**
     * Matrix multiplicate number.
     *
     * This method implements multiplication of current matrix of the [Matrix] type and number of the [Double] type.
     *
     * @param [number] the input number with type Double.
     *
     * @return the result of the multiplication of matrix and number which is represented as new [Matrix] output type.
     *
     * Asymptotic complexity: O(n * m)
     */
    @Throws(Exception::class)
    fun multiply(number: Double): Matrix {
        // index 'i' matches to matrix row and result vector row
        for (i in 0 until this.getN()) {
            // index 'j' matches to matrix column and 'vector' row
            for (j in 0 until this.getM()) {
                this.setElem(i, j, this.getElem(i, j) * number)
            }
        }

        return this
    }

    /**
     * Matrix multiplicate vector.
     *
     * This method implements multiplication of current matrix of the [Matrix] type and vector of the [Vector] type.
     *
     * @param [vector] the input vector.
     *
     * @return the result of the multiplication of matrix and vector which is represented as new [Vector] output type.
     *
     * Asymptotic complexity: O(n * m)
     */
    @Throws(Exception::class)
    fun multiply(vector: Vector): Vector {
        require(this.getM() == vector.getN()) { "The size of 'matrix' does not match to size of 'vector'." }

        val vectorElemsResult: Array<Double> = Array(this.getN()) { 0.0 }

        // index 'i' matches to matrix row and result vector row
        for (i in 0 until this.getN()) {
            // index 'j' matches to matrix column and 'vector' row
            for (j in 0 until this.getM()) {
                vectorElemsResult[i] += this.getElem(i, j) * vector.getElem(j)
            }
        }

        return Vector(vectorElemsResult)
    }

    /**
     * Matrix addition matrix.
     *
     * This method implements matrix addition of current matrix and input matrix of the [Matrix] type.
     *
     * @param [matrix] the input matrix.
     *
     * @return the result of the addition of two matriсes which is represented as new [Matrix] output type.
     *
     * Asymptotic complexity: O(n * m)
     */
    @Throws(Exception::class)
    fun add(matrix: Matrix): Matrix {
        require(this.getN() == matrix.getN() && this.getM() == matrix.getM()) { "The size of this matrix does not match to size of 'matrix'." }

        val result = Matrix(this.getN(), this.getM())

        for (i in 0 until result.getN()) {
            for (j in 0 until result.getM()) {
                result.setElem(i, j, this.getElem(i, j) + matrix.getElem(i, j))
            }
        }
        return result
    }

    /**
     * Matrix substraction matrix.
     *
     * This method implements matrix substraction of current matrix and input matrix of the [Matrix] type.
     *
     * @param [matrix] the input matrix.
     *
     * @return the result of the substraction of two matriсes which is represented as new [Matrix] output type.
     *
     * Asymptotic complexity: O(n * m)
     */
    @Throws(Exception::class)
    fun sub(matrix: Matrix): Matrix {
        require(this.getN() == matrix.getN() && this.getM() == matrix.getM()) { "The size of this matrix does not match to size of 'matrix'." }

        val result = Matrix(this.getN(), this.getM())

        for (i in 0 until result.getN()) {
            for (j in 0 until result.getM()) {
                result.setElem(i, j, this.getElem(i, j) - matrix.getElem(i, j))
            }
        }
        return result
    }

    /**
     * Matrix transposition.
     *
     * This method implements matrix transposition of current matrix of the [Matrix] type.
     *
     * @return the result of the transposition of matrix which is represented as new [Matrix] output type.
     *
     * Asymptotic complexity: O(n * m)
     */
    fun transpose(): Matrix {
        val matrixElems: Array<Array<Double>> = this.getElems()
        val resultMatrixElems = Array(matrixElems[0].size) { Array(matrixElems.size) { 0.0 } }

        for (i in matrixElems.indices) {
            for (j in matrixElems[0].indices) {
                resultMatrixElems[j][i] = matrixElems[i][j]
            }
        }

        return Matrix(resultMatrixElems)
    }

    /**
     * Invertible Matrix.
     *
     * Inverse of a matrix exists only if the matrix is non-singular i.e., determinant should not be 0. Using determinant and adjoint, we can easily find the inverse of a square matrix.
     *
     * This method implements inverse of matrix of the [Matrix] type.
     *
     * @return the result of the inversion of matrix which is represented as new [Matrix] output type.
     *
     * Asymptotic complexity: O(n^2) * O_det
     */
    fun invertible(): Matrix? {
        val n = this.getN()
        val det = this.determinantWithGauss()
        if (det == 0.0) {
            println("Singular matrix, can't find its inverse")
            return null
        }

        val inverseMatrix = Matrix(n, n)
        val adjMatrix = this.adjoint()

        for (i in 0 until n) {
            for (j in 0 until n) {
                inverseMatrix.setElem(i, j, adjMatrix.getElem(i, j) / det)
            }
        }
        return inverseMatrix
    }

    private fun getCofactor(mat: Matrix, temp: Matrix, p: Int, q: Int, n: Int) {
        var i = 0
        var j = 0

        for (row in 0 until n) {
            for (col in 0 until n) {
                if (row != p && col != q) {
                    temp.setElem(i, j++, mat.getElem(row, col))
                    if (j == n - 1) {
                        j = 0
                        i++
                    }
                }
            }
        }
    }

    /**
     * Matrix determinant.
     *
     * This is a classic method that calculates matrix determinant of the [Double] type.
     *
     * @param [n] the row size of input matrix.
     *
     * @return the result of the calculation of determinant of current matrix which is represented as [Double] output type.
     *
     * Asymptotic complexity: O(n!)
     */
    fun determinant(n: Int): Double {
        if (n == 1) return this.getElem(0, 0)

        val temp = Matrix(n, n)
        var sign = 1
        var det = 0.0

        for (f in 0 until n) {
            getCofactor(this, temp, 0, f, n)
            det += sign * this.getElem(0, f) * temp.determinant(n - 1)
            sign = -sign
        }
        return det
    }

    /**
     * Matrix determinant with using Gauss Method.
     *
     * This is a method that calculates matrix determinant of the [Double] type with using upper triangular matrix.
     *
     * @param [n] the row size of input matrix.
     *
     * @return the result of the calculation of determinant of current matrix which is represented as [Double] output type.
     *
     * Asymptotic complexity: O(n^3)
     */
    fun determinantWithGauss(): Double {
        val upperTriangleMatrix = getUpperTriangularMatrix()
        var res = 1.0
        for (i in 0 until upperTriangleMatrix.n) {
            res *= upperTriangleMatrix.elems[i][i]
        }
        return res
    }

    /**
     * Upper triangular matrix.
     *
     * This is a method that convert matrix to upper triangular matrix of the [Matrix] type with using Gauss Method.
     *
     * @param [n] the row size of input matrix.
     *
     * @return the result of the calculation of determinant of current matrix which is represented as [Double] output type.
     *
     * Asymptotic complexity: O(n^3)
     */
    fun getUpperTriangularMatrix(): Matrix {
        // Validation of the matrix and vector sizes
        if (this.elems.size != this.elems[0].size) {
            throw IllegalArgumentException("The size of 'inputA' matrix must be square (the number of rows must match the number of columns).")
        }

        val matrixClone: Array<Array<Double>> = this.elems.map { it.clone() }.toTypedArray()
        val n: Int = matrixClone.size

        for (k in 0 until n) {
            for (i in k + 1 until n) {
                val scaler = matrixClone[i][k] / matrixClone[k][k]
                for (j in 0 until n) {
                    matrixClone[i][j] = matrixClone[i][j] - scaler * matrixClone[k][j]
                }
            }
        }

        return Matrix(matrixClone)
    }

    /**
     * Adjoint of a Matrix.
     *
     * Adjoint (or Adjugate) of a matrix is the matrix obtained by taking the transpose of the cofactor matrix of a given square matrix is called its Adjoint or Adjugate matrix. The Adjoint of any square matrix ‘A’ (say) is represented as Adj(A).
     *
     * Product of a square matrix A with its adjoint yields a diagonal matrix, where each diagonal entry is equal to determinant of A.
     *
     * A non-zero square matrix ‘A’ of order n is said to be invertible if there exists a unique square matrix ‘B’ of order n such that,
     * adj(AB) = (adj B).(adj A)
     * adj( k A) = kn-1 adj(A)
     * A-1 = (adj A) / |A|
     * (A-1)-1 = A
     * (AB)-1 = B-1A-1
     *
     * This method implements adjoint matrix of the [Matrix] type.
     *
     * @return the result of adjoint of matrix which is represented as new [Matrix] output type.
     */
    fun adjoint(): Matrix {
        if (n == 1) {
            val adj = Matrix(1, 1)
            adj.setElem(0, 0, 1.0)
            return adj
        }

        val adj = Matrix(n, n)
        var sign: Int
        val temp = Matrix(n, n)

        for (i in 0 until n) {
            for (j in 0 until n) {
                getCofactor(this, temp, i, j, n)
                sign = if ((i + j) % 2 == 0) 1 else -1
                adj.setElem(j, i, sign * temp.determinant(n - 1))
            }
        }
        return adj
    }

    /**
     * Matrix norm.
     *
     * This method calculates matrix norm of the [Double] type.
     *
     * @return the result of the calculation of norm of current matrix which is represented as [Double] output type.
     *
     * Asymptotic complexity: O(n * m)
     */
    fun norm(): Double {
        var max = -1.0
        val sumVec = Vector(this.getM())
        for (i in 0 until this.getM()) {
            var sum = 0.0
            for (j in 0 until this.getN()) {
                sum += abs(this.getElem(j, i))
            }
            sumVec.setElem(i, sum)
        }
        for (x in sumVec.getElems()) {
            if (max <= x) {
                max = x
            }
        }
        return max
    }

    /**
     * Checks if a matrix is unitriangular.
     *
     * If the entries on the main diagonal of a triangular matrix are all 1, the matrix is called unitriangular.
     *
     * @return the result of the checking if current matrix is unitriangular which is represented as [Boolean] output type.
     */
    fun isUnitriangularMatrix(): Boolean {
        for (i in 0 until this.getN()) {
            for (j in 0 until this.getM()) {
                if (i == j) {
                    if (this.getElem(i, j) != 1.0) {
                        return false
                    }
                }
            }
        }
        return true
    }

    fun multiplicationDiagonalElements(): Double {
        var res = 1.0
        for (i in 0 until max(n, m)) {
            res *= this.getElem(i, i)
            if (res.isInfinite() || res.isNaN()) {
                return Double.MAX_VALUE
            }
        }
        /*for (i in 0 until this.getN()) {
            for (j in 0 until this.getM()) {
                if (i == j) {
                    res *= this.getElem(i, j)
                    if (res.isInfinite() || res.isNaN()) {
                        return Double.MAX_VALUE
                    }
                }
            }
        }*/
        return res
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Matrix

        if (n != other.n) return false
        if (m != other.m) return false
        if (!elems.contentDeepEquals(other.elems)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = n
        result = 31 * result + m
        result = 31 * result + elems.contentDeepHashCode()
        return result
    }

    override fun toString(): String {
        var elemsStr: String = "\n"
        for (i in elems.indices) {
            elemsStr += "\t"
            for (elem in elems[i]) {
                elemsStr += "$elem "
            }
            elemsStr += "\n"
        }
        return "Matrix{" +
                "elems=[" + elemsStr +
                "], n=" + n +
                ", m=" + m +
                '}'
    }
}