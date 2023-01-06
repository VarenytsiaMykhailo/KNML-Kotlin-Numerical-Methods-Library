package com.github.varenytsiamykhailo.knml.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import kotlinx.coroutines.withContext
import java.util.concurrent.CompletableFuture

/**
 * Strassen algorithm implementation.
 *
 * In linear algebra, the Strassen algorithm, named after Volker Strassen, is an algorithm for
 * matrix multiplication. It is faster than the standard matrix multiplication algorithm for large
 * matrices, with a better asymptotic complexity, although the naive algorithm is often better for
 * smaller matrices. The Strassen algorithm is slower than the fastest known algorithms for
 * extremely large matrices, but such galactic algorithms are not useful in practice, as they are
 * much slower for matrices of practical size. For small matrices even faster algorithms exist.
 *
 * Strassen's algorithm works for any ring, such as plus/multiply, but not all semirings, such as
 * min-plus or boolean algebra, where the naive algorithm still works, and so called combinatorial
 * matrix multiplication.
 *
 * Asymptotic complexity: O(n^log_2(7)).
 *
 * **See Also:** [https://en.wikipedia.org/wiki/Strassen_algorithm], [https://ru.wikipedia.org/wiki/Алгоритм_Штрассена]
 */
class StrassenAlgorithm {
    /**
     * Matrix multiplication with using Strassen algorithm.
     *
     * This method implements matrix multiplication of current matrix and input matrix of the [Matrix] type.
     *
     * @param [A] the first matrix.
     * @param [B] the second matrix.
     *
     * @return the result of the multiplication of two matrices which is represented as new [Matrix] output type.
     *
     * Asymptotic complexity: O(n^log_2(7))
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
            // M1:=(A1+A3)×(B1+B2)
            val M1 = multiply(A11.add(A22), B11.add(B22))

            // M2:=(A2+A4)×(B3+B4)
            val M2 = multiply(A21.add(A22), B11)

            // M3:=(A1−A4)×(B1+A4)
            val M3 = multiply(A11, B12.sub(B22))

            // M4:=A1×(B2−B4)
            val M4 = multiply(A22, B21.sub(B11))

            // M5:=(A3+A4)×(B1)
            val M5 = multiply(A11.add(A12), B22)

            // M6:=(A1+A2)×(B4)
            val M6 = multiply(A21.sub(A11), B11.add(B12))

            // M7:=A4×(B3−B1)
            val M7 = multiply(A12.sub(A22), B21.add(B22))

            // P:=M2+M3−M6−M7
            val C11 = M1.add(M4).sub(M5).add(M7)

            // Q:=M4+M6
            val C12 = M3.add(M5)

            // R:=M5+M7
            val C21 = M2.add(M4)

            // S:=M1−M3−M4−M5
            val C22 = M1.add(M3).sub(M2).add(M6)

            // Step 3: Join 4 halves into one result matrix
            join(C11, R, 0, 0)
            join(C12, R, 0, n / 2)
            join(C21, R, n / 2, 0)
            join(C22, R, n / 2, n / 2)
        }

        return R
    }

    /**
     * Matrix async multiplication with using Strassen algorithm.
     * It can be called only from Kotlin code and need coroutines libraries.
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
            // M1:=(A1+A3)×(B1+B2)
            val M1 = withContext(Dispatchers.IO) { multiplyAsync(A11.add(A22), B11.add(B22)) }

            // M2:=(A2+A4)×(B3+B4)
            val M2 = withContext(Dispatchers.IO) { multiplyAsync(A21.add(A22), B11) }

            // M3:=(A1−A4)×(B1+A4)
            val M3 = withContext(Dispatchers.IO) { multiplyAsync(A11, B12.sub(B22)) }

            // M4:=A1×(B2−B4)
            val M4 = withContext(Dispatchers.IO) { multiplyAsync(A22, B21.sub(B11)) }

            // M5:=(A3+A4)×(B1)
            val M5 = withContext(Dispatchers.IO) { multiplyAsync(A11.add(A12), B22) }

            // M6:=(A1+A2)×(B4)
            val M6 = withContext(Dispatchers.IO) { multiplyAsync(A21.sub(A11), B11.add(B12)) }

            // M7:=A4×(B3−B1)
            val M7 = withContext(Dispatchers.IO) { multiplyAsync(A12.sub(A22), B21.add(B22)) }

            // P:=M2+M3−M6−M7
            val C11 = M1.add(M4).sub(M5).add(M7)

            // Q:=M4+M6
            val C12 = M3.add(M5)

            // R:=M5+M7
            val C21 = M2.add(M4)

            // S:=M1−M3−M4−M5
            val C22 = M1.add(M3).sub(M2).add(M6)

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
     * Matrix async multiplication with using Strassen algorithm.
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