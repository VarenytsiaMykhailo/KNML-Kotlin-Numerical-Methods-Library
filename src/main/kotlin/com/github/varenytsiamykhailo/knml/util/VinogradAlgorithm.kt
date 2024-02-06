package com.github.varenytsiamykhailo.knml.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import kotlinx.coroutines.withContext
import java.util.concurrent.CompletableFuture

/**
 * Vinograd algorithm implementation.
 *
 * In linear algebra, the Vinograd algorithm, is an algorithm for matrix multiplication.
 * It is faster than the standard matrix multiplication algorithm for large
 * matrices, with a better asymptotic complexity, although the naive algorithm is often better for
 * smaller matrices. The Vinograd algorithm is much slower for matrices of practical size that
 * other fast matrix multiplication algorithms, such as Strassen algorithm, but it faster on very
 * large matrices. For small matrices even faster algorithms exist.
 *
 * Asymptotic complexity: O(n^2.3755).
 *
 * **See Also:** [https://ru.wikipedia.org/wiki/Алгоритм_Копперсмита_—_Винограда]
 */

class VinogradAlgorithm {
    /**
     * Matrix multiplication with using Vinograd algorithm.
     *
     * This method implements matrix multiplication of current matrix and input matrix of the [Matrix] type.
     *
     * @param [A] the first matrix.
     * @param [B] the second matrix.
     *
     * @return the result of the multiplication of two matrices which is represented as new [Matrix] output type.
     *
     * Asymptotic complexity: O(n^2.3755)
     */
    fun multiply(A: Matrix, B: Matrix): Matrix {

        val n = A.getN()
        val m = A.getM()
        val p = B.getM()

        val R = Matrix(n, p)

        val rowFactor = DoubleArray(n)
        val colFactor = DoubleArray(p)

        // Precompute row factors
        for (i in 0 until n) {
            for (j in 0 until m / 2) {
                rowFactor[i] += A.getElem(i,2 * j) * A.getElem(i, 2 * j + 1)
            }
        }

        // Precompute column factors
        for (i in 0 until p) {
            for (j in 0 until m / 2) {
                colFactor[i] += B.getElem(2 * j, i) * B.getElem(2 * j + 1, i)
            }
        }

        // Compute matrix multiplication
        for (i in 0 until n) {
            for (j in 0 until p) {
                R.setElem(i, j, -rowFactor[i] - colFactor[j])
                for (k in 0 until m / 2) {
                    R.setElem(i, j, (A.getElem(i, 2 * k) + B.getElem(2 * k + 1, j)) *
                                         (A.getElem(i, 2 * k + 1) + B.getElem(2 * k, j)))
                }
            }
        }

        // Adjust for odd matrix size
        if (m % 2 != 0) {
            for (i in 0 until n) {
                for (j in 0 until p) {
                    R.setElem(i, j,  R.getElem(i, j) + A.getElem(i, m - 1) *
                                                            B.getElem(m - 1, j))
                }
            }
        }

        return R
    }

    /**
     * Matrix async multiplication with using Vinograd algorithm.
     * It can be called only from Kotlin code and need coroutines libraries.
     *
     * This method implements matrix multiplication of current matrix and input matrix of the [Matrix] type.
     *
     * @param [A] the first matrix.
     * @param [B] the second matrix.
     *
     * @return the result of the multiplication of two matrices which is represented as new [Matrix] output type.
     *
     * This should not give much optimization. Supposed to be uses in academic purposes.
     */
    suspend fun multiplyAsync(m1: Matrix, m2: Matrix): Matrix {
        val n = m1.getN()
        val m = m2.getM()
        val p = m2.getM()

        val R = Matrix(n, p)

        val rowFactor = withContext(Dispatchers.IO) { calculateRowFactor(m1, n, m)}
        val colFactor = withContext(Dispatchers.IO) { calculateColFactor(m2, p, m)}

        // Compute matrix multiplication
        for (i in 0 until n) {
            for (j in 0 until p) {
                R.setElem(i, j, -rowFactor[i] - colFactor[j])
                for (k in 0 until m / 2) {
                    R.setElem(i, j, (m1.getElem(i, 2 * k) + m2.getElem(2 * k + 1, j)) *
                            (m1.getElem(i, 2 * k + 1) + m2.getElem(2 * k, j)))
                }
            }
        }

        // Adjust for odd matrix size
        if (m % 2 != 0) {
            for (i in 0 until n) {
                for (j in 0 until p) {
                    R.setElem(i, j,  R.getElem(i, j) + m1.getElem(i, m - 1) *
                            m2.getElem(m - 1, j))
                }
            }
        }

        return R
    }

    private fun calculateRowFactor(A: Matrix, n: Int, m: Int): DoubleArray {
        val rowFactor = DoubleArray(n)

        // Precompute row factors
        for (i in 0 until n) {
            for (j in 0 until m / 2) {
                rowFactor[i] += A.getElem(i,2 * j) * A.getElem(i, 2 * j + 1)
            }
        }
        return rowFactor
    }

    private fun calculateColFactor(A: Matrix, p: Int, m: Int): DoubleArray {
        val colFactor = DoubleArray(p)

        // Precompute column factors
        for (i in 0 until p) {
            for (j in 0 until m / 2) {
                colFactor[i] += A.getElem(2 * j, i) * A.getElem(2 * j + 1, i)
            }
        }

        return colFactor
    }

    /**
     * Matrix async multiplication with using Vinograd algorithm.
     * It can be called from Java code.
     *
     * This method implements matrix multiplication of current matrix and input matrix of the [Matrix] type.
     *
     * @param [A] the first matrix.
     * @param [B] the second matrix.
     *
     * @return the result of the multiplication of two matrices which is represented as new [Matrix] output type.
     *
     * Asymptotic complexity: ~O(n^2.55)
     */
    fun multiplyAsyncFuture(A: Matrix, B: Matrix): CompletableFuture<Matrix> =
        GlobalScope.future { multiplyAsync(A, B) }

}