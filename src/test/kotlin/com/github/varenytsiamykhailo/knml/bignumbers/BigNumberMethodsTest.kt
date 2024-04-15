package com.github.varenytsiamykhailo.knml.bignumbers

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class BigNumberMethodsTest {
    @Test
    fun testPlus() {
        val x = BigNumber("2")
        val y = BigNumber("2001")
        assertEquals("2003", (x+y).number)
    }

    @Test
    fun testPlus2() {
        val x = BigNumber("12193121840000000")
        val y = BigNumber(   "13128433387466")
        assertEquals(    "12206250273387466", (x+y).number)
    }

    @Test
    fun testPlusWithFirstMinus() {
        val x = BigNumber("2")
        val y = BigNumber("-2001")
        assertEquals("-1999", (x+y).number)
    }

    @Test
    fun testPlusWithSecondMinus() {
        val x = BigNumber("12193121840000000")
        val y = BigNumber(   "13128433387466")
        assertEquals(    "12206250273387466", (x+y).number)
    }

    @Test
    fun testMinus() {
        val x = BigNumber("2")
        val y = BigNumber("2001")
        assertEquals("-1999", (x-y).number)
    }

    @Test
    fun testMinusWithFirstMinus() {
        val x = BigNumber("-2")
        val y = BigNumber(   "1999")
        assertEquals(    "-2001", (x-y).number)
    }

    @Test
    fun testMinusWithSecondMinus() {
        val x = BigNumber("2")
        val y = BigNumber("-2001")
        assertEquals("2003", (x-y).number)
    }

    @Test
    fun testMul2() {
        val x = BigNumber("10")
        val y = BigNumber(   "2")
        assertEquals(    "20", (x.mul(y)).number)
    }
}
