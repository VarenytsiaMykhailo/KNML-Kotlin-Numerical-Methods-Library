package com.github.varenytsiamykhailo.knml.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

internal class VinogradStrassenAlgorithmTest {
    @Test
    fun test() {
        val s = VinogradStrassenAlgorithm()

        val multiplyArr = mutableListOf<Long>()
        val strassenArr = mutableListOf<Long>()
        val strassenWithcoroutinesArr = mutableListOf<Long>()

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
                    strassenArr.add((System.currentTimeMillis() - startTime))
                }

                A = getMatrixWithRandomElementsAndDiagonalDominance(size, 0, 15, 1)
                B = getMatrixWithRandomElementsAndDiagonalDominance(size, 0, 15, 1)

                startTime = System.currentTimeMillis()
                s.multiplyAsync(A, B).run {
                    strassenWithcoroutinesArr.add((System.currentTimeMillis() - startTime))
                }
            }
        }.also {
            for (i in sizeArray.indices) {
                println(sizeArray[i])
                println("Multiply matrix: " + multiplyArr[i])
                println("Vinograd-Strassen: " + strassenArr[i])
                println("Vinograd-Strassen with coroutines: " + strassenWithcoroutinesArr[i])
                println()
            }
        }
    }
}