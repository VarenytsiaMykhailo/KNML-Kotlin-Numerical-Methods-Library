package com.github.varenytsiamykhailo.knml.integralmethods

import com.github.varenytsiamykhailo.knml.integralmethods.solutions.RectangleMethodRichardsonLoopIterationValues
import com.github.varenytsiamykhailo.knml.integralmethods.solutions.RectangleMethodSolution
import com.github.varenytsiamykhailo.knml.util.results.DoubleResultWithStatus
import com.github.varenytsiamykhailo.knml.util.getMachineEps

/**
 * Rectangle method implementation.
 *
 * The method of rectangles is a method of numerical integration of a function of one variable,
 * which consists in replacing the integrand with a polynomial of degree zero, that is, a constant, on each elementary segment.
 * If we consider the graph of the integrand, the method will consist in the approximate calculation of the area under
 * the graph by summing the areas of a finite number of rectangles,
 * the width of which will be determined by the distance between the corresponding neighboring integration nodes,
 * and the height - by the value of the integrand at these nodes.
 *
 * Asymptotic complexity: O(n^2 * T(integralFunction)), where T - asymptotic complexity of the integral function.
 * For example, if the asymptotic complexity of the integral function is O(n^3),
 * then the asymptotic complexity of the rectangle method will be O(n^2 * n^3) = O(n^5)
 *
 * Use [solveIntegralByRectangleMethod] method to solve the integral.
 *
 * **See Also:** [https://en.wikipedia.org/wiki/Riemann_sum], [https://ru.wikipedia.org/wiki/Метод_прямоугольников]
 */
class RectangleMethod {

    /**
     * Rectangle method implementation.
     *
     * The method of rectangles is a method of numerical integration of a function of one variable,
     * which consists in replacing the integrand with a polynomial of degree zero, that is, a constant, on each elementary segment.
     * If we consider the graph of the integrand, the method will consist in the approximate calculation of the area under
     * the graph by summing the areas of a finite number of rectangles,
     * the width of which will be determined by the distance between the corresponding neighboring integration nodes,
     * and the height - by the value of the integrand at these nodes.
     *
     * Asymptotic complexity: O(n^2 * T(integralFunction)), where T - asymptotic complexity of the integral function.
     *
     * **See Also:** [https://en.wikipedia.org/wiki/Riemann_sum], [https://ru.wikipedia.org/wiki/Метод_прямоугольников]
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
    fun solveIntegralByRectangleMethod(
        intervalStart: Double,
        intervalEnd: Double,
        eps: Double = getMachineEps(),
        formSolution: Boolean = false,
        integralFunction: (x: Double) -> Double
    ): DoubleResultWithStatus {
        return try {
            runSolvingIntegralByRectangleMethod(
                intervalStart,
                intervalEnd,
                eps,
                formSolution,
                integralFunction
            )
        } catch (e: Exception) {
            DoubleResultWithStatus(null, false, e, null)
        }
    }

    private fun runSolvingIntegralByRectangleMethod(
        intervalStart: Double,
        intervalEnd: Double,
        eps: Double,
        formSolution: Boolean,
        integralFunction: (x: Double) -> Double
    ): DoubleResultWithStatus {

        var solutionString: String = ""
        val solution: RectangleMethodSolution = RectangleMethodSolution()

        if (formSolution) solutionString += "The fully integral solution of the rectangle method.\n"

        var result: Double = 0.0
        val numOfSplits: Int =
            2 // Initial num of splits. It will double in each iteration of the Richardson precision-method loop
        var R: Double = 0.0 // Richardson Extrapolation
        val p: Int = 2 // Precision order for the rectangle method for the Richardson Extrapolation
        if (formSolution) solutionString += "Coefficient 'p' of the Richardson extrapolation formula is 2 for the rectangle method.\n"

        if (formSolution) solutionString += "The rectangle algorithm will run until precision eps = $eps is reached which is controlled by Richardson extrapolation loop:\nRecalculated absolute 'R' value should be smaller than 'eps'.\n"
        var numOfSplitsTmp = numOfSplits
        var loopIterationsCount = 1
        // Richardson Extrapolation method
        do {
            result = runRectangleMethod(intervalStart, intervalEnd, numOfSplitsTmp, integralFunction)
            R = (runRectangleMethod(
                intervalStart,
                intervalEnd,
                numOfSplitsTmp * 2,
                integralFunction
            ) - result) / ((1 shl p) - 1)
            if (formSolution) {
                solutionString += "The $loopIterationsCount iteration of the Richards extrapolation method. Calculated rectangle method result is $result, calculated 'R' is $R, num of splits is $numOfSplitsTmp.\n"
                solution.rectangleMethodRichardsonLoopIterations.add(RectangleMethodRichardsonLoopIterationValues(result, R, numOfSplitsTmp, loopIterationsCount))
            }
            numOfSplitsTmp *= 2
            loopIterationsCount++
        } while (Math.abs(R) > eps)

        if (formSolution) solutionString += "The absolute value of 'R' = $R is smaller than 'eps' = $eps, required precision has achieved.\n"

        result += R
        if (formSolution) {
            solutionString += "The integral solution is the rectangle method result plus 'R': $result.\n"
            solution.solutionString = solutionString
        }

        return DoubleResultWithStatus(
            result,
            isSuccessful = true,
            errorException = null,
            solutionObject = if (formSolution) solution else null
        )
    }


    private fun runRectangleMethod(
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
            I += integralFunction(splitStartPointer + 0.5 * h)
            splitStartPointer += h
        }
        I *= h

        return I
    }
}