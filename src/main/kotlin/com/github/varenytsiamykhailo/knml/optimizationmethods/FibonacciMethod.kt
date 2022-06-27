package com.github.varenytsiamykhailo.knml.optimizationmethods

import com.github.varenytsiamykhailo.knml.optimizationmethods.solutions.FibonacciMethodSolution
import com.github.varenytsiamykhailo.knml.util.getMachineEps
import com.github.varenytsiamykhailo.knml.util.results.DoubleResultWithStatus


/**
 * Fibonacci method implementation.
 *
 * The Fibonacci method is an improvement on the search implementation using the [GoldenSectionMethod],
 * which is used to find the minimum / maximum of a function. Like the golden section method,
 * it requires two evaluations of the function at the first iteration,
 * and only one at each subsequent iteration. However, this method differs from the golden section
 * method in that the reduction factor for the uncertainty interval varies from iteration to iteration.
 * The method is based on the Fibonacci number sequence.
 *
 * Use [findExtremaByFibonacciMethod] method to find extremum point of the input function.
 *
 * **See Also:** [https://en.wikipedia.org/wiki/Fibonacci_search_technique], [https://neerc.ifmo.ru/wiki/index.php?title=Метод_Фибоначчи]
 */
class FibonacciMethod {

    /**
     * Fibonacci method implementation.
     *
     * The Fibonacci method is an improvement on the search implementation using the [GoldenSectionMethod],
     * which is used to find the minimum / maximum of a function. Like the golden section method,
     * it requires two evaluations of the function at the first iteration,
     * and only one at each subsequent iteration. However, this method differs from the golden section
     * method in that the reduction factor for the uncertainty interval varies from iteration to iteration.
     * The method is based on the Fibonacci number sequence.
     *
     * Use [findExtremaByFibonacciMethod] method to find extremum point of the input function.
     *
     * **See Also:** [https://en.wikipedia.org/wiki/Fibonacci_search_technique], [https://neerc.ifmo.ru/wiki/index.php?title=Метод_Фибоначчи]
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
    fun findExtremaByFibonacciMethod(
        intervalStart: Double,
        intervalEnd: Double,
        eps: Double? = null,
        formSolution: Boolean = false,
        function: (x: Double) -> Double
    ): DoubleResultWithStatus {
        return try {
            runFindExtremaByFibonacciMethod(
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

    private fun runFindExtremaByFibonacciMethod(
        intervalStart: Double,
        intervalEnd: Double,
        eps: Double,
        formSolution: Boolean,
        function: (x: Double) -> Double
    ): DoubleResultWithStatus {

        var solutionString: String = ""
        val solution: FibonacciMethodSolution = FibonacciMethodSolution()

        if (formSolution) solutionString += "The fully solution of the Fibonacci find extremum method.\n"

        var intervalStartTmp: Double = intervalStart
        var intervalEndTmp: Double = intervalEnd
        val epsTmp: Double = eps
        var n = 0
        do {
            n++
        } while (getFibNumber(n + 2) < (intervalEndTmp - intervalStartTmp) / epsTmp)
        var c: Double
        var d: Double
        c = intervalStartTmp + (intervalEndTmp - intervalStartTmp) * getFibNumber(n) / getFibNumber(n + 2)
        d = intervalStartTmp + (intervalEndTmp - intervalStartTmp) * getFibNumber(n + 1) / getFibNumber(n + 2)
        var itersCount = 0
        while (n >= 2) {
            n--
            if (function(c) <= function(d)) {
                intervalEndTmp = d
                d = c
                c = intervalStartTmp + (intervalEndTmp - intervalStartTmp) * getFibNumber(n) / getFibNumber(n + 2)
            } else {
                intervalStartTmp = c
                c = d
                d = intervalStartTmp + (intervalEndTmp - intervalStartTmp) * getFibNumber(n + 1) / getFibNumber(n + 2)
            }
            itersCount++
        }

        val result: Double = (intervalEndTmp + intervalStartTmp) / 2

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

    fun getFibNumber(n: Int): Int {
        var x1 = 1
        var x2 = 1
        for (i in 3..n) {
            val t = x1 + x2
            x1 = x2
            x2 = t
        }

        return x2
    }

}