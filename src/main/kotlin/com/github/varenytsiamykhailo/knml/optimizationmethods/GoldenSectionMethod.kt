package com.github.varenytsiamykhailo.knml.optimizationmethods

import com.github.varenytsiamykhailo.knml.optimizationmethods.solutions.FibonacciMethodSolution
import com.github.varenytsiamykhailo.knml.util.getMachineEps
import com.github.varenytsiamykhailo.knml.util.results.DoubleResultWithStatus

/**
 * Golden section method implementation.
 *
 * The golden-section search is a technique for finding an extremum (minimum or maximum)
 * of a function inside a specified interval. For a strictly unimodal function with an extremum
 * inside the interval, it will find that extremum, while for an interval containing multiple extrema
 * (possibly including the interval boundaries), it will converge to one of them.
 * If the only extremum on the interval is on a boundary of the interval,
 * it will converge to that boundary point. The method operates by successively narrowing the
 * range of values on the specified interval, which makes it relatively slow, but very robust.
 *
 * Use [findExtremaByGoldenSectionMethod] method to find extremum point of the input function.
 *
 * **See Also:** [https://en.wikipedia.org/wiki/Golden-section_search], [https://ru.wikipedia.org/wiki/Метод_золотого_сечения#:~:text=Метод%20золотого%20сечения%20—%20метод%20поиска,Джеком%20Кифером%20в%201953%20году.]
 */
class GoldenSectionMethod {

    /**
     * Golden section method implementation.
     *
     * The golden-section search is a technique for finding an extremum (minimum or maximum)
     * of a function inside a specified interval. For a strictly unimodal function with an extremum
     * inside the interval, it will find that extremum, while for an interval containing multiple extrema
     * (possibly including the interval boundaries), it will converge to one of them.
     * If the only extremum on the interval is on a boundary of the interval,
     * it will converge to that boundary point. The method operates by successively narrowing the
     * range of values on the specified interval, which makes it relatively slow, but very robust.
     *
     * Use [findExtremaByGoldenSectionMethod] method to find extremum point of the input function.
     *
     * **See Also:** [https://en.wikipedia.org/wiki/Golden-section_search], [https://ru.wikipedia.org/wiki/Метод_золотого_сечения#:~:text=Метод%20золотого%20сечения%20—%20метод%20поиска,Джеком%20Кифером%20в%201953%20году.]
     *
     * @param [intervalStart] is the start of the interval to search extremum.
     * @param [intervalEnd]  is the end of the interval to search extremum.
     * @param [eps] is the input required precision of the result.
     * The user can use, for example, 'eps = 0.001' if he need quickly solution with low error.
     * If the user does not pass their required precision, then will be used default machine precision as the most accurate precision.
     * @param [formSolution] is the flag, that says that the method need to form a solution object with the text of a detailed solution.
     * @param [function] is the  function, which extremum will be search.
     *
     * @return This method returns approximate extremum point of the input function which is wrapped into [DoubleResultWithStatus] object.
     * This object also contains solution of [Double] value, successful flag, error-exception object if unsuccess, and solution object if needed.
     */
    fun findExtremaByGoldenSectionMethod(
        intervalStart: Double,
        intervalEnd: Double,
        eps: Double? = null,
        formSolution: Boolean = false,
        function: (x: Double) -> Double
    ): DoubleResultWithStatus {
        return try {
            runFindExtremaByGoldenSectionMethod(
                intervalStart,
                intervalEnd,
                eps ?: getMachineEps(),
                formSolution,
                function
            )
        } catch (e: Exception) {
            DoubleResultWithStatus(null, false, e, null)
        }
    }

    private fun runFindExtremaByGoldenSectionMethod(
        intervalStart: Double,
        intervalEnd: Double,
        eps: Double,
        formSolution: Boolean,
        function: (x: Double) -> Double
    ): DoubleResultWithStatus {

        var solutionString: String = ""
        val solution: FibonacciMethodSolution = FibonacciMethodSolution()

        if (formSolution) solutionString += "The fully solution of the golden section find extremum method.\n"

        var intervalStartTmp: Double = intervalStart
        var intervalEndTmp: Double = intervalEnd
        val epsTmp: Double = eps

        var y: Double = intervalStartTmp + (3 - Math.sqrt(5.0)) * (intervalEndTmp - intervalStartTmp) / 2
        var z: Double = intervalStartTmp + intervalEndTmp - y

        var delta: Double = Math.abs(intervalStartTmp - intervalEndTmp)
        var itersCount = 0
        while (delta > epsTmp) {
            val yVal: Double = function(y)
            val zVal: Double = function(z)
            if (yVal <= zVal) {
                intervalEndTmp = z
                z = y
                y = intervalStartTmp + intervalEndTmp - y
            } else if (yVal > zVal) {
                intervalStartTmp = y
                y = z
                z = intervalStartTmp + intervalEndTmp - z
            }
            delta = Math.abs(intervalStartTmp - intervalEndTmp)
            itersCount++
        }

        val result: Double = (intervalStartTmp + intervalEndTmp) / 2

        if (formSolution) {
            solutionString += "The result is: ${result}.\n"
            solution.solutionString = solutionString
        }

        return DoubleResultWithStatus(
            result,
            isSuccessful = true,
            errorException = null,
            solutionObject = if (formSolution) solution else null
        )
    }
}