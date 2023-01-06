package com.github.varenytsiamykhailo.knml.util

import org.junit.jupiter.api.Test

class QRDecompositionTest {

    @Test
    fun testQRDecomposition() {
        //val matrix = getMatrixWithRandomElementsAndDiagonalDominance(10, 0, 10, 1)
        val matrix = Matrix(
            arrayOf(
                arrayOf(1.0, 2.0, 4.0),
                arrayOf(3.0, 3.0, 2.0),
                arrayOf(4.0, 1.0, 3.0),
            )
        )
        println("Matrix: $matrix")

        val result = QRDecomposition(matrix)

        println(result.Q)
        println(result.R)
    }
}