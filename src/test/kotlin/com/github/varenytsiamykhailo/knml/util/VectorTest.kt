package com.github.varenytsiamykhailo.knml.util

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

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

    @Test
    fun testAdd() {
        val v1: Vector = Vector(arrayOf(1.0, 2.5, 2.0))
        val v2: Vector = Vector(arrayOf(-1.0, 3.0, 6.0))

        val addResult = v1.add(v2)
        val expectedResult = Vector(arrayOf(0.0, 5.5, 8.0))

        assertEquals(addResult, expectedResult)
    }

    @Test
    fun testAddWithException() {
        val v1: Vector = Vector(arrayOf(1.0, 2.5, 2.0))
        val v2: Vector = Vector(arrayOf(-1.0, 3.0, 6.0, 7.0))

        assertThrows<Exception> {
            v1.add(v2)
        }
    }

    @Test
    fun testSub() {
        val v1: Vector = Vector(arrayOf(1.0, 2.5, 2.0))
        val v2: Vector = Vector(arrayOf(-1.0, 3.0, 6.0))

        val subResult = v1.sub(v2)
        val expectedResult = Vector(arrayOf(2.0, -0.5, -4.0))

        assertEquals(subResult, expectedResult)
    }

    @Test
    fun testSubWithException() {
        val v1: Vector = Vector(arrayOf(1.0, 2.5, 2.0))
        val v2: Vector = Vector(arrayOf(-1.0, 3.0))

        assertThrows<Exception> {
            v1.sub(v2)
        }
    }

    @Test
    fun testNorm() {
        val v: Vector = Vector(arrayOf(1.0, 2.5, 2.0))

        val result = v.norm()
        val expectedResult = 3.3541019662496847

        assertEquals(result, expectedResult)
    }
}