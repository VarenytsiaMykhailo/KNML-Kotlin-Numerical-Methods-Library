package com.github.varenytsiamykhailo.knml.bignumbers

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class ToomCookMethodTest {
    @Test
    fun toomCookTest() {
        val x = BigNumber("1234567890123456789012")
        val y = BigNumber("987654321987654321098")

        val multiplyMethod = MultiplyMethod()
        val sum = multiplyMethod.toomCook3WayMultiplication(x, y)

        Assertions.assertEquals("1219326312467611632493760095208585886175176", sum.number)
    }

    @Test
    fun toomCookTestWithOneSmallNumber() {
        val x = BigNumber("101010101010")
        val y = BigNumber("2")

        val multiplyMethod = MultiplyMethod()
        val sum = multiplyMethod.toomCook3WayMultiplication(x, y)

        Assertions.assertEquals("202020202020", sum.number)
    }

    @Test
    fun toomCookTestWithSecondSmallNumber() {
        val x = BigNumber("2")
        val y = BigNumber("101010101010")

        val multiplyMethod = MultiplyMethod()
        val sum = multiplyMethod.toomCook3WayMultiplication(x, y)

        Assertions.assertEquals("202020202020", sum.number)
    }

    @Test
    fun toomCookTestWithAnotherLengthNumbers() {
        val x = BigNumber("101010101010")
        val y = BigNumber("222")

        val multiplyMethod = MultiplyMethod()
        val sum = multiplyMethod.toomCook3WayMultiplication(x, y)

        Assertions.assertEquals("22424242424220", sum.number)
    }
}
