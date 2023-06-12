package com.github.varenytsiamykhailo.knml.util

import com.github.varenytsiamykhailo.knml.systemsolvingmethods.GaussMethod
import com.github.varenytsiamykhailo.knml.util.analyzer.InputAnalyzer
import com.github.varenytsiamykhailo.knml.util.results.VectorResultWithStatus
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class AnalyzerTest {
    @Test
    fun testAddVectors() {
        val result = InputAnalyzer().analyzeAndSolve(
            "Add {{1.0, 2.0}, {4.0, 5.0}} {{5.0, 6.0}, {7.0, 8.0}}"
        )
        println(result)
        val expectedResult = Vector(arrayOf(2.0, 4.0, 2.0))
        Assertions.assertEquals(result, expectedResult)
    }

    @Test
    fun testSubVectors() {
        val result = InputAnalyzer().analyzeAndSolve(
            "sub {1.0, 2.0, 3.0} {1.0, 2.0, -1.0}"
        )
        println(result)
        val expectedResult = Vector(arrayOf(0.0, 0.0, 4.0))
        Assertions.assertEquals(result, expectedResult)
    }

    @Test
    fun testAddMatrices() {
        val result = InputAnalyzer().analyzeAndSolve(
            "add {{2.0, -3.0, 1.0}, {5.0, 4.0, -2.0}} {{4.0, 2.0, -5.0}, {-4.0, 1.0, 3.0}}"
        )
        println(result)
        val expectedResult = Matrix(
            arrayOf(
                arrayOf(6.0, -1.0, -4.0),
                arrayOf(1.0, 5.0, 1.0),
            )
        )
        Assertions.assertEquals(result, expectedResult)
    }

    @Test
    fun testSubMatrices() {
        val result = InputAnalyzer().analyzeAndSolve(
            "sub {{2.0, -3.0, 1.0}, {5.0, 4.0, -2.0}} {{4.0, 2.0, -5.0}, {-4.0, 1.0, 3.0}}"
        )
        println(result)
        val expectedResult = Matrix(
            arrayOf(
                arrayOf(-2.0, -5.0, 6.0),
                arrayOf(9.0, 3.0, -5.0),
            )
        )
        Assertions.assertEquals(result, expectedResult)
    }

    @Test
    fun testTransposeMatrix() {
        val result = InputAnalyzer().analyzeAndSolve(
            "transpose {{2.0, -3.0}, {4.0, 8.0}, {5.0, 7.0}}"
        )
        println(result)
        val expectedResult = Matrix(
            arrayOf(
                arrayOf(2.0, 4.0, 5.0),
                arrayOf(-3.0, 8.0, 7.0)
            )
        )
        Assertions.assertEquals(result, expectedResult)
    }

    @Test
    fun testMulMatrixNumber() {
        val result = InputAnalyzer().analyzeAndSolve(
            "mul {{2.0, 4.0, 0.0}, {-2.0, 1.0, 3.0}, {-1.0, 0.0, 1.0}} 5.0"
        )
        println(result)
        val expectedResult = Matrix(
            arrayOf(
                arrayOf(10.0, 20.0, 0.0),
                arrayOf(-10.0, 5.0, 15.0),
                arrayOf(-5.0, 0.0, 5.0)
            )
        )
        Assertions.assertEquals(result, expectedResult)
    }

    @Test
    fun testMulMatrixVector() {
        val result = InputAnalyzer().analyzeAndSolve(
            "mul {{2.0, 4.0, 0.0}, {-2.0, 1.0, 3.0}, {-1.0, 0.0, 1.0}} {1.0, 2.0, -1.0}"
        )
        println(result)
        val expectedResult = Vector(arrayOf(10.0, -3.0, -2.0))
        Assertions.assertEquals(result, expectedResult)
    }

    @Test
    fun testMulMatrices() {
        val result = InputAnalyzer().analyzeAndSolve(
            "mul {{5.0, 8.0, -4.0}, {6.0, 9.0, -5.0}, {4.0, 7.0, -3.0}} {{3.0, 2.0, 5.0}, {4.0, -1.0, 3.0}, {9.0, 6.0, 5.0}}"
        )
        println(result)
        val expectedResult = Matrix(
            arrayOf(
                arrayOf(11.0, -22.0, 29.0),
                arrayOf(9.0, -27.0, 32.0),
                arrayOf(13.0, -17.0, 26.0)
            )
        )
        Assertions.assertEquals(result, expectedResult)
    }

    @Test
    fun testLUDecomposition() {
        val result = InputAnalyzer().analyzeAndSolve(
            "lu {{1.0, 2.0, 1.0}, {2.0, 1.0, 1.0}, {1.0, -1.0, 2.0}}"
        )
        println((result as LUDecomposition).lowerMatrix)
        println(result.upperMatrix)
        val expectedLowerResult = Matrix(
            arrayOf(
                arrayOf(1.0, 0.0, 0.0),
                arrayOf(2.0, 1.0, 0.0),
                arrayOf(1.0, 1.0, 1.0)
            )
        )
        val expectedUpperResult = Matrix(
            arrayOf(
                arrayOf(1.0, 2.0, 1.0),
                arrayOf(0.0, -3.0, -1.0),
                arrayOf(0.0, 0.0, 2.0)
            )
        )
        Assertions.assertEquals(result.lowerMatrix, expectedLowerResult)
        Assertions.assertEquals(result.upperMatrix, expectedUpperResult)
    }

    @Test
    fun testGaussMethod() {
        val result = InputAnalyzer().analyzeAndSolve(
            "gauss method {1.0, 2.0} {{1.0, 2.0}, {3.0, 4.0}}"
        )
        println((result as VectorResultWithStatus).arrayResult)
        val expectedResult = arrayOf(0.0, 0.5)
        Assertions.assertEquals(result.arrayResult?.toList(), expectedResult.toList())
    }

    @Test
    fun testGaussMethod2() {
        val m1 = Matrix(arrayOf(arrayOf(2.0, -1.0, 0.0), arrayOf(5.0, 4.0, 2.0), arrayOf(0.0, 1.0, -3.0)))
        val v1 = Vector(arrayOf(3.0, 6.0, 2.0))
        val res = GaussMethod().solveSystemByGaussClassicMethod(
            m1,
            v1,
            formSolution = true
        )
        val result = InputAnalyzer().analyzeAndSolve(
            "gauss method {3.0, 6.0, 2.0} {{2.0, -1.0, 0.0}, {5.0, 4.0, 2.0}, {0.0, 1.0, -3.0}}"
        )
        println((result as VectorResultWithStatus).arrayResult?.toList())
        println("res: $res")
    }
}