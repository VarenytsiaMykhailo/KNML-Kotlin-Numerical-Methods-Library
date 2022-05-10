package com.github.varenytsiamykhailo.knml.util

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

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
}