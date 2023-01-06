package com.github.varenytsiamykhailo.knml.util

/**
 * Matrix LU decomposition.
 *
 * This method calculates matrix LU decomposition of the [Double] type.
 *
 * @param [matrix] is the input matrix.
 *
 * @return the result of the calculation of LU decomposition of current matrix
 */
class LUDecomposition(private var matrix: Matrix) {
    private val n = matrix.getN()

    var lowerMatrix = Matrix(n, n)

    var upperMatrix = Matrix(n, n)

    /**
     * Returns lower triangular matrix.
     *
     * @return This method returns lower triangular matrix of [Matrix] type
     */
    fun getLowerTriangularMatrix(): Matrix {
        return lowerMatrix
    }

    /**
     * Returns upper triangular matrix.
     *
     * @return This method returns upper triangular matrix of [Matrix] type
     */
    fun getUpperTriangularMatrix(): Matrix {
        return upperMatrix
    }

    init {
        for (i in 0 until n) {
            // Upper Triangular
            for (k in i until n) {
                var sum = 0.0
                for (j in 0 until i) {
                    sum += lowerMatrix.getElem(i, j) * upperMatrix.getElem(j, k)
                }
                upperMatrix.setElem(i, k, matrix.getElem(i, k) - sum)
            }

            // Lower Triangular
            for (k in i until n) {
                if (i == k) {
                    lowerMatrix.setElem(i, i, 1.0)
                } else {
                    var sum = 0.0
                    for (j in 0 until i) {
                        sum += lowerMatrix.getElem(k, j) * upperMatrix.getElem(j, i)
                    }
                    lowerMatrix.setElem(k, i, (matrix.getElem(k, i) - sum) / upperMatrix.getElem(i, i))
                }
            }
        }
    }

    /**
     * Matrix determinant.
     *
     * This method calculates matrix determinant of the [Double] type.
     *
     * @param [lowerMatrix] the lower matrix of input matrix LU decomposition.
     * @param [upperMatrix] the upper matrix of input matrix LU decomposition.
     *
     * @return the result of the calculation of determinant of current matrix which is represented as [Double] output type.
     */
    fun determinant(): Double {
        val upperMultiply = upperMatrix.multiplicationDiagonalElements()
        val lowerMultiply = lowerMatrix.multiplicationDiagonalElements()
        println("$upperMultiply $lowerMultiply")
        return if (upperMultiply == 1.0) {
            lowerMultiply
        } else {
            upperMultiply
        }
    }
}