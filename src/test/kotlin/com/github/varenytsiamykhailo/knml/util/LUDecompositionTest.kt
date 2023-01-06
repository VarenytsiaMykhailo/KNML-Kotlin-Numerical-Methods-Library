package com.github.varenytsiamykhailo.knml.util

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.math.roundToInt

class LUDecompositionTest {

    @Test
    fun testLUDecomposition() {
        //val matrix = getMatrixWithRandomElementsAndDiagonalDominance(10, 0, 10, 1)
        val matrix = Matrix(
            arrayOf(
                arrayOf(1.0, 2.0, 1.0),
                arrayOf(2.0, 1.0, 1.0),
                arrayOf(1.0, -1.0, 2.0),
            )
        )
        println("Matrix: $matrix")

        val result = LUDecomposition(matrix)

        val mulMatrix = result.lowerMatrix.multiply(result.upperMatrix)

        println("\nLower matrix: " + result.lowerMatrix)
        println("\nUpper matrix: " + result.upperMatrix)

        val roundMatrix = mulMatrix.getElems()
        roundMatrix.forEach {
            it.forEach { num -> num.roundToInt() }
        }
        println("\nMultiplication of lower and upper matrix: ${Matrix(roundMatrix)}")

        val determinant = result.determinant()
        println("Determinant from LU decomposition: $determinant")
        println(matrix.determinant(matrix.getN()))
        Assertions.assertEquals(determinant, matrix.determinant(matrix.getN()))
    }
}