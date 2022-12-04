package com.github.varenytsiamykhailo.knml.util

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.math.roundToInt

internal class MatrixTest {

    @Test
    fun testMatrix() {
        val m1: Matrix = Matrix(2, 3)
        assertEquals(2, m1.getN())
        assertEquals(3, m1.getM())
        assertArrayEquals(
            arrayOf(
                arrayOf(0.0, 0.0, 0.0),
                arrayOf(0.0, 0.0, 0.0),
            ),
            m1.getElems()
        )

        val m2: Matrix = Matrix(
            arrayOf(
                arrayOf(100.0, 30.0, -70.0),
                arrayOf(15.0, -50.0, -5.0),
                arrayOf(6.0, 2.0, -20.0)
            )
        )
        assertEquals(3, m2.getN())
        assertEquals(3, m2.getM())
        assertArrayEquals(
            arrayOf(
                arrayOf(100.0, 30.0, -70.0),
                arrayOf(15.0, -50.0, -5.0),
                arrayOf(6.0, 2.0, -20.0)
            ),
            m2.getElems()
        )

        assertEquals(-5.0, m2.getElem(1, 2))
        m2.setElem(1,2, 15.0)
        assertEquals(15.0, m2.getElem(1, 2))

        m2.setElems(arrayOf(arrayOf(15.0, 22.9)))
        assertEquals(1, m2.getN())
        assertEquals(2, m2.getM())
        assertArrayEquals(arrayOf(arrayOf(15.0, 22.9)), m2.getElems())
    }

    @Test
    fun testEquals() {
        val m1: Matrix = Matrix(
            arrayOf(
                arrayOf(100.0, 30.0, -70.0),
                arrayOf(15.0, -50.0, -5.0),
                arrayOf(6.0, 2.0, -20.0)
            )
        )
        val m2: Matrix = Matrix(2, 3)

        assert(m1 == m1)
        assert(m2 == m2)
        assert(m1 != m2)
    }

    @Test
    fun testAdd() {
        val m1 = Matrix(
            arrayOf(
                arrayOf(2.0, -3.0, 1.0),
                arrayOf(5.0, 4.0, -2.0),
            )
        )
        val m2 = Matrix(
            arrayOf(
                arrayOf(4.0, 2.0, -5.0),
                arrayOf(-4.0, 1.0, 3.0),
            )
        )

        val addResult = m1.add(m2)
        val expectedResult = Matrix(
            arrayOf(
                arrayOf(6.0, -1.0, -4.0),
                arrayOf(1.0, 5.0, 1.0),
            )
        )

        assert(addResult == expectedResult)
    }

    @Test
    fun testAddWithException() {
        val m1 = Matrix(
            arrayOf(
                arrayOf(2.0, -3.0, 1.0),
                arrayOf(5.0, 4.0, -2.0),
            )
        )
        val m2 = Matrix(
            arrayOf(
                arrayOf(4.0, 2.0),
                arrayOf(-4.0, 1.0),
                arrayOf(-4.0, 1.0),
            )
        )

        assertThrows<IllegalArgumentException> {
            m1.add(m2)
        }
    }

    @Test
    fun testSub() {
        val m1 = Matrix(
            arrayOf(
                arrayOf(2.0, -3.0, 1.0),
                arrayOf(5.0, 4.0, -2.0),
            )
        )
        val m2 = Matrix(
            arrayOf(
                arrayOf(4.0, 2.0, -5.0),
                arrayOf(-4.0, 1.0, 3.0),
            )
        )

        val subResult = m1.sub(m2)
        val expectedResult = Matrix(
            arrayOf(
                arrayOf(-2.0, -5.0, 6.0),
                arrayOf(9.0, 3.0, -5.0),
            )
        )

        assert(subResult == expectedResult)
    }

    @Test
    fun testSubWithException() {
        val m1 = Matrix(
            arrayOf(
                arrayOf(2.0, -3.0, 1.0),
                arrayOf(5.0, 4.0, -2.0),
            )
        )
        val m2 = Matrix(
            arrayOf(
                arrayOf(4.0, 2.0),
                arrayOf(-4.0, 1.0),
                arrayOf(-4.0, 1.0),
            )
        )

        assertThrows<IllegalArgumentException> {
            m1.sub(m2)
        }
    }

    @Test
    fun testMultiplyConst() {
        val m = Matrix(
            arrayOf(
                arrayOf(2.0, -3.0, 1.0),
                arrayOf(5.0, 4.0, -2.0),
            )
        )
        val number = 3.0

        val multiplyResult = m.multiply(number)
        val expectedResult = Matrix(
            arrayOf(
                arrayOf(6.0, -9.0, 3.0),
                arrayOf(15.0, 12.0, -6.0),
            )
        )

        assert(multiplyResult == expectedResult)
    }

    @Test
    fun testMultiplyVector() {
        val m = Matrix(
            arrayOf(
                arrayOf(2.0, 4.0, 0.0),
                arrayOf(-2.0, 1.0, 3.0),
                arrayOf(-1.0, 0.0, 1.0)
            )
        )
        val vector = Vector(
            arrayOf(1.0, 2.0, -1.0)
        )

        val multiplyResult = m.multiply(vector)
        val expectedResult = Vector(
            arrayOf(10.0, -3.0, -2.0),
        )

        assert(multiplyResult == expectedResult)
    }

    @Test
    fun testMultiplyVectorWithException() {
        val m = Matrix(
            arrayOf(
                arrayOf(2.0, 4.0, 0.0, 5.0),
                arrayOf(-2.0, 1.0, 3.0, 6.0),
                arrayOf(-1.0, 0.0, 1.0, 7.0)
            )
        )
        val vector = Vector(
            arrayOf(1.0, 2.0, -1.0)
        )

        assertThrows<IllegalArgumentException> {
            m.multiply(vector)
        }
    }

    @Test
    fun testMultiplyMatrix() {
        val m1 = Matrix(
            arrayOf(
                arrayOf(2.0, 1.0),
                arrayOf(-3.0, 0.0),
                arrayOf(4.0, -1.0)
            )
        )
        val m2 = Matrix(
            arrayOf(
                arrayOf(5.0, -1.0, 6.0),
                arrayOf(-3.0, 0.0, 7.0),
            )
        )

        val multiplyResult = m1.multiply(m2)
        val expectedResult = Matrix(
            arrayOf(
                arrayOf(7.0, -2.0, 19.0),
                arrayOf(-15.0, 3.0, -18.0),
                arrayOf(23.0, -4.0, 17.0)
            )
        )

        assert(multiplyResult == expectedResult)
    }

    @Test
    fun testMultiplyMatrixWithException() {
        val m1 = Matrix(
            arrayOf(
                arrayOf(2.0, 1.0),
                arrayOf(-3.0, 0.0),
                arrayOf(4.0, -1.0)
            )
        )
        val m2 = Matrix(
            arrayOf(
                arrayOf(5.0, -1.0, 5.0, 6.0),
                arrayOf(-3.0, 0.0, 7.0, 8.0),
                arrayOf(-3.0, 0.0, 7.0, 8.0),
            )
        )

        assertThrows<IllegalArgumentException> {
            m1.multiply(m2)
        }
    }

    @Test
    fun testNorm() {
        val m = Matrix(
            arrayOf(
                arrayOf(2.0, 3.0, 1.0),
                arrayOf(1.0, 9.0, 4.0),
                arrayOf(2.0, 6.0, 7.0)
            )
        )

        val norm = m.norm()
        val expectedResult = 18.0

        assertEquals(norm, expectedResult)
    }

    @Test
    fun testNorm1() {
        val m = Matrix(
            arrayOf(
                arrayOf(8.0, 8.0),
                arrayOf(3.0, -2.0),
                arrayOf(8.0, -8.0)
            )
        )

        val norm = m.norm()
        val expectedResult = 19.0

        assertEquals(norm, expectedResult)
    }

    @Test
    fun testTranspose() {
        val m = Matrix(
            arrayOf(
                arrayOf(2.0, -3.0),
                arrayOf(4.0, 8.0),
                arrayOf(5.0, 7.0)
            )
        )

        val transposeResult = m.transpose()
        val expectedResult = Matrix(
            arrayOf(
                arrayOf(2.0, 4.0, 5.0),
                arrayOf(-3.0, 8.0, 7.0)
            )
        )

        assertEquals(transposeResult, expectedResult)
    }

    @Test
    fun testAdjoint() {
        val m = Matrix(
            arrayOf(
                arrayOf(5.0, 1.0, -3.0),
                arrayOf(9.0, 2.0, 4.0),
                arrayOf(-1.0, 6.0, 1.0)
            )
        )

        val adjointResult = m.adjoint()
        val expectedResult = Matrix(
            arrayOf(
                arrayOf(-22.0, -19.0, 10.0),
                arrayOf(-13.0, 2.0, -47.0),
                arrayOf(56.0, -31.0, 1.0)
            )
        )

        assertEquals(adjointResult, expectedResult)
    }

    // 'v1' scalar moultiplication to 'v2' should be equals '12.0'
    @Test
    fun testScalarProduct() {
        val v1: Vector = Vector(arrayOf(1.0, 2.5, 2.0))
        val v2: Vector = Vector(arrayOf(2.0, 2.0, 2.5))
        val result: Double = v1.scalarProduct(v2)

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
        val resultVector: Vector = m1.multiply(v1)

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
        val resultMatrix: Matrix = m1.multiply(m2)

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
        val m4: Matrix = Matrix(
            arrayOf(
                arrayOf(1.0, 2.0, 3.0),
                arrayOf(4.0, 5.0, 6.0)
            )
        )
        val resultMatrix: Matrix = m4.transpose()

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

    @Test
    fun testInverseMatrix() {
        val m4 = Matrix(
            arrayOf(
                arrayOf(5.0, -2.0, 2.0, 7.0),
                arrayOf(1.0, 0.0, 0.0, 3.0),
                arrayOf(-3.0, 1.0, 5.0, 0.0),
                arrayOf(3.0, -1.0, -9.0, 4.0)
            )
        )
        val resultMatrix: Matrix? = m4.invertible()

        println(resultMatrix)
    }

    @Test
    fun testLUDecomposition() {
        val matrix = getMatrixWithRandomElementsAndDiagonalDominance(10, 0, 10, 1)
        /*val matrix = Matrix(
            arrayOf(
                arrayOf(1.0, 2.0, 1.0),
                arrayOf(2.0, 1.0, 1.0),
                arrayOf(1.0, -1.0, 2.0),
            )
        )*/
        println("Matrix: $matrix")

        val result = matrix.getLUDecomposition()

        val mulMatrix = result.first.multiply(result.second)

        println("\nLower matrix: " + result.first)
        println("\nUpper matrix: " + result.second)

        val roundMatrix = mulMatrix.getElems()
        roundMatrix.forEach {
            it.forEach { num -> num.roundToInt() }
        }
        println("\nMultiplication of lower and upper matrix: ${Matrix(roundMatrix)}")

        val determinant = matrix.determinant(result.first, result.second)
        println("Determinant from LU decomposition: " + determinant)
    }
}