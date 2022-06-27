package com.github.varenytsiamykhailo.knml.optimizationmethods

import com.github.varenytsiamykhailo.knml.util.results.DoubleResultWithStatus
import org.junit.jupiter.api.Test
import kotlin.math.abs

internal class GoldenSectionMethodTest {

    @Test
    fun test1FindExtremaByGoldenSectionMethod() {

        val result: DoubleResultWithStatus = GoldenSectionMethod().findExtremaByGoldenSectionMethod(
            -10.0,
            7.0,
            0.001,
            true
        ) { x ->
            (x - 1) * (x - 1) + abs(x + 5) * abs(x + 5)
        }

        assert(result.doubleResult.toString().startsWith("-2.00008"))
        assert(result.isSuccessful)
        assert(result.errorException == null)
        assert(result.solutionObject != null)
        assert(result.solutionObject!!.solutionString.length >= 10)
    }

}