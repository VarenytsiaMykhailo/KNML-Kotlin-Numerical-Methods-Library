package com.github.varenytsiamykhailo.knml.util

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class VectorTest {

    @Test
    fun testVector() {
        val v1: Vector = Vector(arrayOf(13.0, 15.3, 28.9))

        // 2 + 5 + 5 = 12
        assertEquals(3, v1.getN())
        assertArrayEquals(arrayOf(13.0, 15.3, 28.9), v1.getElems())

        assertEquals(28.9, v1.getElem(2))
        v1.setElem(2, -123.0)
        assertEquals(-123.0, v1.getElem(2))

        v1.setElems(arrayOf(1.0, 2.0))
        assertEquals(2, v1.getN())
        assertArrayEquals(arrayOf(1.0, 2.0), v1.getElems())
    }

    @Test
    fun testEquals() {
        val v1: Vector = Vector(arrayOf(1.0, 2.5, 2.0))
        val v2: Vector = Vector(arrayOf(1.0, 2.5, 2.0))
        val v3: Vector = Vector(arrayOf(2.0, 2.0, 2.5, 4.08))

        assert(v1.equals(v2))
        assert(!v1.equals(v3))
        assert(!v2.equals(v3))
    }
}