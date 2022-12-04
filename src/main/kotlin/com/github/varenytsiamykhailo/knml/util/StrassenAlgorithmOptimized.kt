package com.github.varenytsiamykhailo.knml.util

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking


class StrassenAlgorithmOptimized {
    suspend fun multiply(m1: Matrix, m2: Matrix): Matrix {
        val n = m1.getN()
        val R = Matrix(n, n)

        // Base case
        // If there is only single element
        if (n == 1) { // Returning the simple multiplication of two elements in matrices
            R.setElem(0, 0, m1.getElem(0, 0) * m2.getElem(0, 0))
        } else {
            // Step 1: Dividing Matrix into parts
            // by storing sub-parts to variables
            val A11 = Matrix(n / 2, n / 2)
            val A12 = Matrix(n / 2, n / 2)
            val A21 = Matrix(n / 2, n / 2)
            val A22 = Matrix(n / 2, n / 2)
            val B11 = Matrix(n / 2, n / 2)
            val B12 = Matrix(n / 2, n / 2)
            val B21 = Matrix(n / 2, n / 2)
            val B22 = Matrix(n / 2, n / 2)

            // Step 2: Dividing matrix A into 4 halves
            split(m1, A11, 0, 0)
            split(m1, A12, 0, n / 2)
            split(m1, A21, n / 2, 0)
            split(m1, A22, n / 2, n / 2)

            // Step 2: Dividing matrix B into 4 halves
            split(m2, B11, 0, 0)
            split(m2, B12, 0, n / 2)
            split(m2, B21, n / 2, 0)
            split(m2, B22, n / 2, n / 2)

            // Using Formulas as described in algorithm
            // M1:=(A1+A3)×(B1+B2)
            val M1 = runBlocking { async { multiply(A11.add(A22), B11.add(B22)) } }

            // M2:=(A2+A4)×(B3+B4)
            val M2 = runBlocking { async { multiply(A21.add(A22), B11) } }

            // M3:=(A1−A4)×(B1+A4)
            val M3 = runBlocking { async { multiply(A11, B12.sub(B22)) } }

            // M4:=A1×(B2−B4)
            val M4 = runBlocking { async { multiply(A22, B21.sub(B11)) } }

            // M5:=(A3+A4)×(B1)
            val M5 = runBlocking { async { multiply(A11.add(A12), B22) } }

            // M6:=(A1+A2)×(B4)
            val M6 = runBlocking { async { multiply(A21.sub(A11), B11.add(B12)) } }

            // M7:=A4×(B3−B1)
            val M7 = runBlocking { async { multiply(A12.sub(A22), B21.add(B22)) } }

            // P:=M2+M3−M6−M7
            val C11 = (((M1.await()).add(M4.await())).sub(M5.await())).add(M7.await())

            // Q:=M4+M6
            val C12 = (M3.await()).add(M5.await())

            // R:=M5+M7
            val C21 = (M2.await()).add(M4.await())

            // S:=M1−M3−M4−M5
            val C22 = (((M1.await()).add(M3.await())).sub(M2.await())).add(M6.await())

            // Step 3: Join 4 halves into one result matrix
            join(C11, R, 0, 0)
            join(C12, R, 0, n / 2)
            join(C21, R, n / 2, 0)
            join(C22, R, n / 2, n / 2)
        }

        return R
    }

    // Function to split parent matrix into child matrices
    fun split(P: Matrix, C: Matrix, iB: Int, jB: Int) {
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
    fun join(C: Matrix, P: Matrix, iB: Int, jB: Int) { // Iterating over elements of 2D matrix using nested for loops

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
}

class StrassenAlgorithm {
    fun multiply(A: Matrix, B: Matrix): Matrix {
        val n = A.getN()
        val R = Matrix(n, n)

        // Base case
        // If there is only single element
        if (n == 1) { // Returning the simple multiplication of two elements in matrices
            R.setElem(0, 0, A.getElem(0, 0) * B.getElem(0, 0))
        } else {
            // Step 1: Dividing Matrix into parts
            // by storing sub-parts to variables
            val A11 = Matrix(n / 2, n / 2)
            val A12 = Matrix(n / 2, n / 2)
            val A21 = Matrix(n / 2, n / 2)
            val A22 = Matrix(n / 2, n / 2)
            val B11 = Matrix(n / 2, n / 2)
            val B12 = Matrix(n / 2, n / 2)
            val B21 = Matrix(n / 2, n / 2)
            val B22 = Matrix(n / 2, n / 2)

            // Step 2: Dividing matrix A into 4 halves
            split(A, A11, 0, 0)
            split(A, A12, 0, n / 2)
            split(A, A21, n / 2, 0)
            split(A, A22, n / 2, n / 2)

            // Step 2: Dividing matrix B into 4 halves
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

    // Function to split parent matrix into child matrices
    fun split(P: Matrix, C: Matrix, iB: Int, jB: Int) {
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
    fun join(C: Matrix, P: Matrix, iB: Int, jB: Int) { // Iterating over elements of 2D matrix using nested for loops

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
}

fun main() {
    val s = StrassenAlgorithmOptimized()
    val simpleStrassen = StrassenAlgorithm()

    val multiplyArr = mutableListOf<Long>()
    val strassenArr = mutableListOf<Long>()
    val strassenWithcoroutinesArr = mutableListOf<Long>()

    val sizeArray = arrayOf(50, 100, 200, 300, 400, 500)

    runBlocking {
        sizeArray.forEach { size ->
            var A = getMatrixWithRandomElementsAndDiagonalDominance(size, 5, 15, 1)
            var B = getMatrixWithRandomElementsAndDiagonalDominance(size, 5, 15, 1)

            var startTime = System.currentTimeMillis()
            A.multiply(B).run {
                multiplyArr.add((System.currentTimeMillis() - startTime))
            }

            A = getMatrixWithRandomElementsAndDiagonalDominance(size, 0, 5, 1)
            B = getMatrixWithRandomElementsAndDiagonalDominance(size, 0, 5, 1)

            startTime = System.currentTimeMillis()
            simpleStrassen.multiply(A, B).run {
                strassenArr.add((System.currentTimeMillis() - startTime))
            }

            A = getMatrixWithRandomElementsAndDiagonalDominance(size, 15, 20, 1)
            B = getMatrixWithRandomElementsAndDiagonalDominance(size, 15, 20, 1)

            startTime = System.currentTimeMillis()
            async { s.multiply(A, B) }.await().run {
                strassenWithcoroutinesArr.add((System.currentTimeMillis() - startTime))
            }
        }
    }.also {
        for (i in sizeArray.indices) {
            println(sizeArray[i])
            println("Multiply matrix: " + multiplyArr[i])
            println("Strassen: " + strassenArr[i])
            println("Strassen with coroutines: " + strassenWithcoroutinesArr[i])
            println()
        }
    }
}