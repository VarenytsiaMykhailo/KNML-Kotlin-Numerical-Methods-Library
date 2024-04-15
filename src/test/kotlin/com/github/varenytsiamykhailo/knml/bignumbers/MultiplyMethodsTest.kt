package com.github.varenytsiamykhailo.knml.bignumbers

import kotlin.math.*

fun main() {
    val a = BigNumber("2")
    val b = BigNumber(   "200")
    val res = a.mul(b)
    println("20 ${res.number}")

    val x = BigNumber("1234567890123456789012")
    val y = BigNumber("987654321987654321098")

    val multiplyMethod = MultiplyMethod()
    val result1 = multiplyMethod.karatsuba(x, y)
    val result2 = multiplyMethod.toomCook3WayMultiplication(x, y)
    val result3 = multiplyMethod.schonhageShtrassen(x, y)

    val answer = "1219326312467611632493760095208585886175176"

    println("answer:   $answer")
    println("karatsuba ${result1.number}")
    println("toomCook3 ${result2.number}")
    println("Shtrassen ${result3.number}")
}
