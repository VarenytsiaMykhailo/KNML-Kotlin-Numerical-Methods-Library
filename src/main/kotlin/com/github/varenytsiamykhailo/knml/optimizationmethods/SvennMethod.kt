package com.github.varenytsiamykhailo.knml.optimizationmethods

import com.github.varenytsiamykhailo.knml.optimizationmethods.solutions.SvennMethodSolution
import com.github.varenytsiamykhailo.knml.util.Interval
import com.github.varenytsiamykhailo.knml.util.results.IntervalResultWithStatus

/**
 * Svenn method implementation.
 *
 * Svenn's method is a heuristic method that determines an uncertainty interval containing a minimum extremum point.
 **
 * Use [findIntervalBySvennMin] method to find [Interval] of the extremum.
 */
class SvennMethod {

    /**
     * Svenn method implementation.
     *
     * Svenn's method is a heuristic method that determines an uncertainty interval containing a minimum extremum point.
     **
     * Use [findIntervalBySvennMin] method to find [Interval] of the extremum.
     *
     * @param [xStart] is the start point to search.
     * @param [stepEps] is the input required precision of the result.
     * The user can use, for example, 'eps = 0.001'
     * @param [formSolution] is the flag, that says that the method need to form a solution object with the text of a detailed solution.
     * @param [function] is the function, which extremum will be search.
     *
     * * @return This method returns approximate interval, containing extremum point of the input function which is wrapped into [IntervalResultWithStatus] object.
     * This object also contains solution of [Interval] value, successful flag, error-exception object if unsuccess, and solution object if needed.
     */
    fun findIntervalBySvennMin(
        xStart: Double,
        stepEps: Double = 0.000001,
        formSolution: Boolean = false,
        function: (x: Double) -> Double
    ): IntervalResultWithStatus {
        return try {
            runFindIntervalBySvennMin(xStart, stepEps, formSolution, function)
        } catch (e: Exception) {
            IntervalResultWithStatus(null, false, e, null)
        }
    }

    @Throws(IllegalArgumentException::class)
    private fun runFindIntervalBySvennMin(
        xStart: Double,
        stepEps: Double,
        formSolution: Boolean,
        function: (x: Double) -> Double
    ): IntervalResultWithStatus {

        var solutionString: String = ""
        val solution: SvennMethodSolution = SvennMethodSolution()

        if (formSolution) solutionString += "The fully solution of the Svenn method to find interval for the extremum search.\n"


        var xStartTmp = xStart
        var x1 = xStartTmp
        val stepEpsTmp = stepEps
        var k = 0.0
        var a0 = Double.MIN_VALUE
        var b0 = Double.MAX_VALUE
        var delta = 0.0
        val left: Double = function(xStartTmp - stepEpsTmp)
        val middle: Double = function(xStartTmp)
        val right: Double = function(xStartTmp + stepEpsTmp)

        if (left >= middle && middle <= right) {
            a0 = xStartTmp - stepEpsTmp
            b0 = xStartTmp + stepEpsTmp

            return IntervalResultWithStatus(
                Interval(a0, b0),
                isSuccessful = true,
                errorException = null,
                solutionObject = if (formSolution) solution else null
            )

        } else if (left <= middle && middle >= right) {
            throw IllegalArgumentException("Function is ununimodal. Use another start point.")
        } else {
            if (left >= middle && middle >= right) {
                delta = stepEpsTmp
                a0 = xStartTmp
                xStartTmp = xStartTmp + stepEpsTmp
                k = 1.0
            }
            if (left <= middle && middle <= right) {
                delta = -stepEpsTmp
                b0 = xStartTmp
                xStartTmp = xStartTmp - stepEpsTmp
                k = 1.0
            }
            do {
                x1 = xStartTmp + Math.pow(2.0, k) * delta
                if (function(x1) < function(xStartTmp) && delta == stepEpsTmp) {
                    a0 = xStartTmp
                }
                if (function(x1) < function(xStartTmp) && delta == -stepEpsTmp) {
                    b0 = xStartTmp
                }
                k = k + 1
            } while (function(x1) < function(xStartTmp))
            if (delta == stepEpsTmp) {
                b0 = x1
            } else if (delta == -stepEpsTmp) {
                a0 = x1
            }
        }

        if (formSolution) {
            solutionString += "The result interval is: [${a0}; ${b0}].\n"
            solution.solutionString = solutionString
        }

        return IntervalResultWithStatus(
            Interval(a0, b0),
            isSuccessful = true,
            errorException = null,
            solutionObject = if (formSolution) solution else null
        )
    }

}