package com.github.varenytsiamykhailo.knml.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

internal class VinogradAlgorithmTest {
    @Test
    fun test() {
        val s = VinogradAlgorithm()

        val multiplyArr = mutableListOf<Long>()
        val VinogradArr = mutableListOf<Long>()
        val VinogradWithcoroutinesArr = mutableListOf<Long>()

        val sizeArray = arrayOf(50, 100, 150, 200, 300, 400, 500, 600, 700, 800, 900, 1000)

        runBlocking(Dispatchers.IO) {
            sizeArray.forEach { size ->
                var A = getMatrixWithRandomElementsAndDiagonalDominance(size, 0, 15, 1)
                var B = getMatrixWithRandomElementsAndDiagonalDominance(size, 0, 15, 1)

                var startTime = System.currentTimeMillis()
                A.multiply(B).run {
                    multiplyArr.add((System.currentTimeMillis() - startTime))
                }

                A = getMatrixWithRandomElementsAndDiagonalDominance(size, 0, 15, 1)
                B = getMatrixWithRandomElementsAndDiagonalDominance(size, 0, 15, 1)

                startTime = System.currentTimeMillis()
                s.multiply(A, B).run {
                    VinogradArr.add((System.currentTimeMillis() - startTime))
                }

                A = getMatrixWithRandomElementsAndDiagonalDominance(size, 0, 15, 1)
                B = getMatrixWithRandomElementsAndDiagonalDominance(size, 0, 15, 1)

                startTime = System.currentTimeMillis()
                s.multiplyAsync(A, B).run {
                    VinogradWithcoroutinesArr.add((System.currentTimeMillis() - startTime))
                }
            }
        }.also {
            for (i in sizeArray.indices) {
                println(sizeArray[i])
                println("Multiply matrix: " + multiplyArr[i])
                println("Vinograd: " + VinogradArr[i])
                println("Vinograd with coroutines: " + VinogradWithcoroutinesArr[i])
                println()
            }
        }
    }
}