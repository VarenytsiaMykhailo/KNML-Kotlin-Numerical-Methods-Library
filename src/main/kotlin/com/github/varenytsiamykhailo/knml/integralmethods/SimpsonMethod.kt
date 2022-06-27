package com.github.varenytsiamykhailo.knml.integralmethods

import com.github.varenytsiamykhailo.knml.integralmethods.solutions.SimpsonMethodRichardsonLoopIterationValues
import com.github.varenytsiamykhailo.knml.integralmethods.solutions.SimpsonMethodSolution
import com.github.varenytsiamykhailo.knml.util.results.DoubleResultWithStatus
import com.github.varenytsiamykhailo.knml.util.getMachineEps

/**
 * Simpson method implementation.
 *
 * The Simpson method is a method for the numerical integration of a function of one variable.
 * The essence of Simpson's method is to approximate the integrand on a segment by an interpolation polynomial of the second degree,
 * that is, to approximate the function graph on a segment by a parabola.
 *
 * Asymptotic complexity: O(n^2 * T(integralFunction)), where T - asymptotic complexity of the integral function.
 *
 * Use [solveIntegralBySimpsonMethod] method to solve the integral.
 *
 * **See Also:** [https://en.wikipedia.org/wiki/Simpson%27s_rule], [https://ru.wikipedia.org/wiki/Формула_Симпсона]
 */
class SimpsonMethod {
    /**
     * Simpson method implementation.
     *
     * The Simpson method is a method for the numerical integration of a function of one variable.
     * The essence of Simpson's method is to approximate the integrand on a segment by an interpolation polynomial of the second degree,
     * that is, to approximate the function graph on a segment by a parabola.
     *
     * Asymptotic complexity: O(n^2 * T(integralFunction)), where T - asymptotic complexity of the integral function.
     *
     * **See Also:** [https://en.wikipedia.org/wiki/Simpson%27s_rule], [https://ru.wikipedia.org/wiki/Формула_Симпсона]
     *
     * @param [intervalStart] is the start of integration interval.
     * @param [intervalEnd]  is the end of integration interval.
     * @param [eps] is the input required precision of the result.
     * The user can use, for example, 'eps = 0.001' if he need quickly solution with low error.
     * If the user does not pass their required precision, then will be used default machine precision as the most accurate precision.
     * @param [formSolution] is the flag, that says that the method need to form a solution object with the text of a detailed solution.
     * @param [integralFunction] is the integral function, which will be integrated.
     *
     * @return This method returns approximate integral solution of the input integral function which is wrapped into [DoubleResultWithStatus] object.
     * This object also contains integral solution of [Double] value, successful flag, error-exception object if unsuccess, and solution object if needed.
     */
    fun solveIntegralBySimpsonMethod(
        intervalStart: Double,
        intervalEnd: Double,
        eps: Double? = null,
        formSolution: Boolean = false,
        integralFunction: (x: Double) -> Double
    ): DoubleResultWithStatus {
        return try {
            runSolvingIntegralBySimpsonMethod(
                intervalStart,
                intervalEnd,
                eps ?: getMachineEps(),
                formSolution,
                integralFunction
            )
        } catch (e: Exception) {
            DoubleResultWithStatus(null, false, e, null)
        }
    }

    private fun runSolvingIntegralBySimpsonMethod(
        intervalStart: Double,
        intervalEnd: Double,
        eps: Double,
        formSolution: Boolean,
        integralFunction: (x: Double) -> Double
    ): DoubleResultWithStatus {

        var solutionString: String = ""
        val solution: SimpsonMethodSolution = SimpsonMethodSolution()

        if (formSolution) solutionString += "The fully integral solution of the Simpson method.\n"

        var result: Double = 0.0
        val numOfSplits: Int =
            2 // Initial num of splits. It will double in each iteration of the Richardson precision-method loop
        var R: Double = 0.0 // Richardson Extrapolation
        val p: Int = 4 // Precision order for the Simpson method for the Richardson Extrapolation
        if (formSolution) solutionString += "Coefficient 'p' of the Richardson extrapolation formula is 2 for the Simpson method.\n"

        if (formSolution) solutionString += "The Simpson algorithm will run until precision eps = $eps is reached which is controlled by Richardson extrapolation loop:\nRecalculated absolute 'R' value should be smaller than 'eps'.\n"
        var numOfSplitsTmp = numOfSplits
        var loopIterationsCount = 1
        do {
            result = runSimpsonMethod(intervalStart, intervalEnd, numOfSplitsTmp, integralFunction)
            R = (runSimpsonMethod(
                intervalStart,
                intervalEnd,
                numOfSplitsTmp * 2,
                integralFunction
            ) - result) / ((1 shl p) - 1)
            if (formSolution) {
                solutionString += "The $loopIterationsCount iteration of the Richards extrapolation method. Calculated Simpson method result is $result, calculated 'R' is $R, num of splits is $numOfSplitsTmp.\n"
                solution.simpsonMethodRichardsonLoopIterations.add(
                    SimpsonMethodRichardsonLoopIterationValues(
                        result,
                        R,
                        numOfSplitsTmp,
                        loopIterationsCount
                    )
                )
            }
            numOfSplitsTmp *= 2
            loopIterationsCount++
        } while (Math.abs(R) > eps)

        if (formSolution) solutionString += "The absolute value of 'R' = $R is smaller than 'eps' = $eps, required precision has achieved.\n"

        result += R
        if (formSolution) {
            solutionString += "The integral solution is the Simpson method result plus 'R': $result.\n"
            solution.solutionString = solutionString
        }

        return DoubleResultWithStatus(
            result,
            isSuccessful = true,
            errorException = null,
            solutionObject = if (formSolution) solution else null
        )
    }


    private fun runSimpsonMethod(
        intervalStart: Double,
        intervalEnd: Double,
        numOfSplits: Int,
        integralFunction: (x: Double) -> Double
    ): Double {
        val h: Double =
            (intervalEnd - intervalStart) / numOfSplits // splitLength = (intervalEnd - intervalStart) / numOfSplits
        var I: Double = 0.0
        var splitStartPointer: Double = intervalStart
        for (i in 0..numOfSplits) {
            I += integralFunction(splitStartPointer) + 4 * integralFunction(splitStartPointer + 0.5 * h) + integralFunction(splitStartPointer + h)
            splitStartPointer += h
        }
        I *= (h / 6)

        return I
    }
}