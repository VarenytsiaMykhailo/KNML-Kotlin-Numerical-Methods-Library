package com.github.varenytsiamykhailo.knml.bignumbers

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class KaratsubaMethodTest {
    @Test
    fun karatsubaTestFirstNumberWithMinus() {
        val x = BigNumber("-21988766")
        val y = BigNumber("11199987")

        val multiplyMethod = MultiplyMethod()
        val sum = multiplyMethod.karatsuba(x, y)

        Assertions.assertEquals("-246273893346042", sum.number)
    }

    @Test
    fun karatsubaTestSecondNumberWithMinus() {
        val x = BigNumber("21988766")
        val y = BigNumber("-11199987")

        val multiplyMethod = MultiplyMethod()
        val sum = multiplyMethod.karatsuba(x, y)

        Assertions.assertEquals("-246273893346042", sum.number)
    }

    @Test
    fun karatsubaTestBothNumberWithMinus() {
        val x = BigNumber("-21988766")
        val y = BigNumber("-11199987")

        val multiplyMethod = MultiplyMethod()
        val sum = multiplyMethod.karatsuba(x, y)

        Assertions.assertEquals("246273893346042", sum.number)
    }

    @Test
    fun karatsubaTest() {
        val x = BigNumber("1234567890123456789012")
        val y = BigNumber("987654321987654321098")

        val multiplyMethod = MultiplyMethod()
        val sum = multiplyMethod.karatsuba(x, y)

        Assertions.assertEquals("1219326312467611632493760095208585886175176", sum.number)
    }

    @Test
    fun karatsubaTestWithOneSmallNumber() {
        val x = BigNumber("101010101010")
        val y = BigNumber("2")

        val multiplyMethod = MultiplyMethod()
        val sum = multiplyMethod.karatsuba(x, y)

        Assertions.assertEquals("202020202020", sum.number)
    }

    @Test
    fun karatsubaTestWithAnotherLengthNumbers() {
        val x = BigNumber("101010101010")
        val y = BigNumber("222")

        val multiplyMethod = MultiplyMethod()
        val sum = multiplyMethod.karatsuba(x, y)

        Assertions.assertEquals("22424242424220", sum.number)
    }
}
