package com.github.varenytsiamykhailo.knml.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import kotlinx.coroutines.withContext
import java.util.concurrent.CompletableFuture

/**
 * Vinograd-Strassen algorithm implementation.
 *
 * Vinograd-Strassen is an algorithm for fast matrix multiplication. This algorithm uses the idea of
 * Vinograd algorithm to reduce the number of sums in from 17 in classic Strassen algorithm down to
 * 15. However, for practical usage this algorithm performance is almost identical to Strassen
 * algorithm.
 *
 * Vinograd-Strassen's algorithm works for any ring, such as plus/multiply, but not all semirings,
 * such as min-plus or boolean algebra, where the naive algorithm still works, and so called
 * combinatorial matrix multiplication.
 *
 * Asymptotic complexity: O(n^log_2(7)).
 *
 * **See Also:** [https://singularitykchen.github.io/blog/2021/08/21/Survey-GEMM-Strassen-and-Winograd-Fast-Convolution-Algorithms/#heading-strassenwinograd-algorithm]
 */
class VinogradStrassenAlgorithm {
    /**
     * Matrix multiplication with using Vinograd-Strassen algorithm.
     *
     * This method implements matrix multiplication of current matrix and input matrix of the [Matrix] type.
     *
     * @param [A] the first matrix.
     * @param [B] the second matrix.
     *
     * @return the result of the multiplication of two matrices which is represented as new [Matrix] output type.
     *
     */
    fun multiply(A: Matrix, B: Matrix): Matrix {
        if (A.getN() <= 64) {
            return A.multiply(B)
        }

        val n = A.getN()
        val R = Matrix(n, n)

        // Base case
        // If there is only single element
        if (n == 1) { // Returning the simple multiplication of two elements in matrices
            R.setElem(0, 0, A.getElem(0, 0) * B.getElem(0, 0))
        } else {
            // Dividing Matrix into parts
            // by storing sub-parts to variables
            val A11 = Matrix(n / 2, n / 2)
            val A12 = Matrix(n / 2, n / 2)
            val A21 = Matrix(n / 2, n / 2)
            val A22 = Matrix(n / 2, n / 2)
            val B11 = Matrix(n / 2, n / 2)
            val B12 = Matrix(n / 2, n / 2)
            val B21 = Matrix(n / 2, n / 2)
            val B22 = Matrix(n / 2, n / 2)

            // Dividing matrix A into 4 halves
            split(A, A11, 0, 0)
            split(A, A12, 0, n / 2)
            split(A, A21, n / 2, 0)
            split(A, A22, n / 2, n / 2)

            // Dividing matrix B into 4 halves
            split(B, B11, 0, 0)
            split(B, B12, 0, n / 2)
            split(B, B21, n / 2, 0)
            split(B, B22, n / 2, n / 2)

            // Using Formulas as described in algorithm
            // S1:=A21+A22
            val S1 = A21.add(A22)

            // S1:=A21+A22
            val S2 = S1.sub(A11)

            // S3:=A11+A21
            val S3 = A11.add(A21)

            // S4:=A12-S2
            val S4 = A12.sub(S2)

            // S5:=B21+B11
            val S5 = B21.sub(B11)

            // S6:=B22-S5
            val S6 = B22.sub(S5)

            // S7:=B22-B12
            val S7 = B22.sub(B12)

            // S8:=S6+B21
            val S8 = S6.sub(B21)

            // M1:=S2×S6
            val M1 = multiply(S2, S6)

            // M2:=A11×B11
            val M2 = multiply(A11, B11)

            // M3:=A12×B12
            val M3 = multiply(A12, B21)

            // M4:=S3×S7
            val M4 = multiply(S3, S7)

            // M5:=S1×S5
            val M5 = multiply(S1, S5)

            // M6:=S4×B22
            val M6 = multiply(S4, B22)

            // M7:=A22×S8
            val M7 = multiply(A22, S8)

            // T1:=M1+M2
            val T1 = M1.add(M2)

            // T2:=M1+M2
            val T2 = T1.add(M4)

            // P:=M2+M3
            val C11 = M2.add(M3)

            // Q:=T1+M5+M6
            val C12 = T1.add(M5).add(M6)

            // R:=T2+M7
            val C21 = T2.sub(M7)

            // S:=T2+M5
            val C22 = T2.add(M5)

            // Step 3: Join 4 halves into one result matrix
            join(C11, R, 0, 0)
            join(C12, R, 0, n / 2)
            join(C21, R, n / 2, 0)
            join(C22, R, n / 2, n / 2)
        }

        return R
    }

    /**
     * Matrix async multiplication with using Vinograd-Strassen algorithm.
     * It can be called only from Kotlin code and need coroutines libraries.
     *
     * This method implements matrix multiplication of current matrix and input matrix of the [Matrix] type.
     *
     * @param [A] the first matrix.
     * @param [B] the second matrix.
     *
     * @return the result of the multiplication of two matrices which is represented as new [Matrix] output type.
     *
     */
    suspend fun multiplyAsync(m1: Matrix, m2: Matrix): Matrix {
        if (m1.getN() <= 64) {
            return m1.multiply(m2)
        }

        val n = m1.getN()
        val R = Matrix(n, n)

        // Base case
        // If there is only single element
        if (n == 1) { // Returning the simple multiplication of two elements in matrices
            R.setElem(0, 0, m1.getElem(0, 0) * m2.getElem(0, 0))
        } else {
            // Dividing Matrix into parts
            // by storing sub-parts to variables
            val A11 = Matrix(n / 2, n / 2)
            val A12 = Matrix(n / 2, n / 2)
            val A21 = Matrix(n / 2, n / 2)
            val A22 = Matrix(n / 2, n / 2)
            val B11 = Matrix(n / 2, n / 2)
            val B12 = Matrix(n / 2, n / 2)
            val B21 = Matrix(n / 2, n / 2)
            val B22 = Matrix(n / 2, n / 2)

            // Dividing matrix A into 4 halves
            split(m1, A11, 0, 0)
            split(m1, A12, 0, n / 2)
            split(m1, A21, n / 2, 0)
            split(m1, A22, n / 2, n / 2)

            // Dividing matrix B into 4 halves
            split(m2, B11, 0, 0)
            split(m2, B12, 0, n / 2)
            split(m2, B21, n / 2, 0)
            split(m2, B22, n / 2, n / 2)

            // Using Formulas as described in algorithm
            // Using Formulas as described in algorithm
            // S1:=A21+A22
            val S1 = A21.add(A22)

            // S2:=S1-A11
            val S2 = S1.sub(A11)

            // S3:=A11+A21
            val S3 = A11.add(A21)

            // S4:=A12-S2
            val S4 = A12.sub(S2)

            // S5:=B21+B11
            val S5 = B21.sub(B11)

            // S6:=B22-S5
            val S6 = B22.sub(S5)

            // S7:=B22-B12
            val S7 = B22.sub(B12)

            // S8:=S6+B21
            val S8 = S6.sub(B21)

            // M1:=S2×S6
            val M1 = withContext(Dispatchers.IO) { multiplyAsync(S2, S6) }

            // M2:=A11×B11
            val M2 = withContext(Dispatchers.IO) { multiplyAsync(A11, B11) }

            // M3:=A12×B12
            val M3 = withContext(Dispatchers.IO) { multiplyAsync(A12, B21) }

            // M4:=S3×S7
            val M4 = withContext(Dispatchers.IO) { multiplyAsync(S3, S7) }

            // M5:=S1×S5
            val M5 = withContext(Dispatchers.IO) { multiplyAsync(S1, S5) }

            // M6:=S4×B22
            val M6 = withContext(Dispatchers.IO) { multiplyAsync(S4, B22) }

            // M7:=A22×S8
            val M7 = withContext(Dispatchers.IO) { multiplyAsync(A22, S8) }

            // T1:=M1+M2
            val T1 = M1.add(M2)

            // T2:=M1+M2
            val T2 = T1.add(M4)

            // P:=M2+M3
            val C11 = M2.add(M3)

            // Q:=T1+M5+M6
            val C12 = T1.add(M5).add(M6)

            // R:=T2-M7
            val C21 = T2.sub(M7)

            // S:=T2+M5
            val C22 = T2.add(M5)

            // Join 4 halves into one result matrix
            join(C11, R, 0, 0)
            join(C12, R, 0, n / 2)
            join(C21, R, n / 2, 0)
            join(C22, R, n / 2, n / 2)
        }

        return R
    }

    // Function to split parent matrix into child matrices
    private fun split(P: Matrix, C: Matrix, iB: Int, jB: Int) {
        // Iterating over elements of 2D matrix using nested for loops

        // Outer loop for rows
        var i1 = 0
        var i2 = iB
        while (i1 < C.getN()) {
            // Inner loop for columns
            var j1 = 0
            var j2 = jB
            while (j1 < C.getN()) {
                C.setElem(i1, j1, P.getElem(i2, j2))
                j1++
                j2++
            }
            i1++
            i2++
        }
    }

    // Function to join child matrices into (to) parent matrix
    private fun join(C: Matrix, P: Matrix, iB: Int, jB: Int) { // Iterating over elements of 2D matrix using nested for loops

        // Outer loop for rows
        var i1 = 0
        var i2 = iB
        while (i1 < C.getN()) {
            // Inner loop for columns
            var j1 = 0
            var j2 = jB
            while (j1 < C.getN()) {
                P.setElem(i2, j2, C.getElem(i1, j1))
                j1++
                j2++
            }
            i1++
            i2++
        }
    }

    /**
     * Matrix async multiplication with using Vinograd-Strassen algorithm.
     * It can be called from Java code.
     *
     * This method implements matrix multiplication of current matrix and input matrix of the [Matrix] type.
     *
     * @param [A] the first matrix.
     * @param [B] the second matrix.
     *
     * @return the result of the multiplication of two matrices which is represented as new [Matrix] output type.
     *
     */
    fun multiplyAsyncFuture(A: Matrix, B: Matrix): CompletableFuture<Matrix> =
        GlobalScope.future { multiplyAsync(A, B) }
}