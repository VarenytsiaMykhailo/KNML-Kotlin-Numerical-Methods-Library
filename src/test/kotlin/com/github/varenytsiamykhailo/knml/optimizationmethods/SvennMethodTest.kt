package com.github.varenytsiamykhailo.knml.optimizationmethods

import com.github.varenytsiamykhailo.knml.util.results.IntervalResultWithStatus
import org.junit.jupiter.api.Test
import kotlin.math.abs

internal class SvennMethodTest {

    @Test
    fun test1FindIntervalBySvennMin() {

        val result: IntervalResultWithStatus =
            SvennMethod().findIntervalBySvennMin(-10.0, 0.001, true) { x ->
                (x - 1) * (x - 1) +
                        abs(x + 5) * abs(x + 5)
            }

        assert(result.intervalResult != null)
        assert(result.intervalResult.toString().startsWith("Interval[-9.999; 6.385]"))
        assert(result.isSuccessful)
        assert(result.errorException == null)
        assert(result.solutionObject != null)
        assert(result.solutionObject!!.solutionString.length >= 10)
    }

}