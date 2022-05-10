package com.github.varenytsiamykhailo.knml.util

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class LinearAlgebraUtilTest {

    // 'v1' scalar moultiplication to 'v2' should be equals '12.0'
    @Test
    fun testScalarProduct() {
        val v1: Vector = Vector(arrayOf(1.0, 2.5, 2.0))
        val v2: Vector = Vector(arrayOf(2.0, 2.0, 2.5))
        val result: Double = scalarProduct(v1, v2)

        // 2 + 5 + 5 = 12
        assertEquals(12.0, result)
    }

    // 'v1' moultiplicate to 'v2' should be equals Vector{elems=[6.5 13.0 19.5 26.0], n=4}
    @Test
    fun testMatrixMultiplicateVector() {
        val m1: Matrix = Matrix(
            arrayOf(
                arrayOf(1.0, 1.0, 1.0),
                arrayOf(2.0, 2.0, 2.0),
                arrayOf(3.0, 3.0, 3.0),
                arrayOf(4.0, 4.0, 4.0),
            )
        )
        val v1: Vector = Vector(arrayOf(2.0, 2.0, 2.5))
        val resultVector: Vector = matrixMultiplicateVector(m1, v1)

        assertEquals(4, resultVector.getN())
        assertArrayEquals(arrayOf(6.5, 13.0, 19.5, 26.0), resultVector.getElems())
    }

    // 'm1' moultiplicate to 'm2' should be equals Matrix{elems=[
    //	11.0 -22.0 29.0
    //	9.0 -27.0 32.0
    //	13.0 -17.0 26.0
    //], n=3, m=3}
    @Test
    fun testMatrixMultiplicateMatrix() {
        val m1: Matrix = Matrix(
            arrayOf(
                arrayOf(5.0, 8.0, -4.0),
                arrayOf(6.0, 9.0, -5.0),
                arrayOf(4.0, 7.0, -3.0)
            )
        )
        val m2: Matrix = Matrix(
            arrayOf(
                arrayOf(3.0, 2.0, 5.0),
                arrayOf(4.0, -1.0, 3.0),
                arrayOf(9.0, 6.0, 5.0)
            )
        )
        val resultMatrix: Matrix = matrixMultiplicateMatrix(m1, m2)

        assertEquals(3, resultMatrix.getN())
        assertEquals(3, resultMatrix.getM())
        assertArrayEquals(
            arrayOf(
                arrayOf(11.0, -22.0, 29.0),
                arrayOf(9.0, -27.0, 32.0),
                arrayOf(13.0, -17.0, 26.0)
            ),
            resultMatrix.getElems()
        )
    }

    // 'm1' transposed should be equals Matrix{elems=[
    //	1.0 4.0
    //	2.0 5.0
    //	3.0 6.0
    //], n=3, m=2}
    @Test
    fun testTransposeMatrix() {
        val m4: Matrix = Matrix(arrayOf(
            arrayOf(1.0, 2.0, 3.0),
            arrayOf(4.0, 5.0, 6.0))
        )
        val resultMatrix: Matrix = transposeMatrix(m4)

        assertEquals(3, resultMatrix.getN())
        assertEquals(2, resultMatrix.getM())
        assertArrayEquals(
            arrayOf(
                arrayOf(1.0, 4.0),
                arrayOf(2.0, 5.0),
                arrayOf(3.0, 6.0)
            ),
            resultMatrix.getElems()
        )
    }
}