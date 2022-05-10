package com.github.varenytsiamykhailo.knml.integralmethods

import com.github.varenytsiamykhailo.knml.util.results.DoubleResultWithStatus
import org.junit.jupiter.api.Test
import kotlin.math.exp

internal class SimpsonMethodTest {

    @Test
    fun test1SolveIntegralBySimpsonMethod() {

        val result: DoubleResultWithStatus = SimpsonMethod().solveIntegralBySimpsonMethod(
            0.0,
            1.0,
            eps = 0.00000001,
            formSolution = true
        ) {
            exp(it)
        }

        assert(result.doubleResult != null)
        assert(result.doubleResult.toString().startsWith("1.7182819850804674"))
        assert(result.isSuccessful)
        assert(result.errorException == null)
        assert(result.solutionObject != null)
        assert(result.solutionObject!!.solutionString.length >= 10)
    }

}