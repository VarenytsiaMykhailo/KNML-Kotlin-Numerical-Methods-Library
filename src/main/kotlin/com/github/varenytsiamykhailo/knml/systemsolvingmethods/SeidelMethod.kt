package com.github.varenytsiamykhailo.knml.systemsolvingmethods

import com.github.varenytsiamykhailo.knml.systemsolvingmethods.solutions.SeidelMethodSolution
import com.github.varenytsiamykhailo.knml.util.*
import com.github.varenytsiamykhailo.knml.util.Vector
import com.github.varenytsiamykhailo.knml.util.results.VectorResultWithStatus
import kotlin.math.abs

/**
 * Seidel method implementation.
 *
 * In numerical linear algebra, the Gauss–Seidel method, also known as the Liebmann method or the method of successive displacement,
 * is an iterative method used to solve a system of linear equations and is similar to the [JacobiMethod].
 * The Gauss-Seidel method can be considered as a modification of the Jacobi method, which, as practice shows,
 * requires approximately half the number of iterations compared to the Jacobi method.
 * Though it can be applied to any matrix with non-zero elements on the diagonals,
 * convergence is only guaranteed if the matrix is either strictly diagonally dominant, or symmetric and positive definite.
 *
 * Asymptotic complexity: O(n^3).
 *
 * Use [solveSystemBySeidelMethod] method to solve the strictly diagonally dominant system of linear equations.
 *
 * **See Also:** [https://en.wikipedia.org/wiki/Gauss–Seidel_method], [https://ru.wikipedia.org/wiki/Метод_Гаусса_—_Зейделя_решения_системы_линейных_уравнений]
 */
class SeidelMethod {

    /**
     * Seidel method implementation.
     *
     * In numerical linear algebra, the Gauss–Seidel method, also known as the Liebmann method or the method of successive displacement,
     * is an iterative method used to solve a system of linear equations and is similar to the [JacobiMethod].
     * The Gauss-Seidel method can be considered as a modification of the Jacobi method, which, as practice shows,
     * requires approximately half the number of iterations compared to the Jacobi method.
     * Though it can be applied to any matrix with non-zero elements on the diagonals,
     * convergence is only guaranteed if the matrix is either strictly diagonally dominant, or symmetric and positive definite.
     *
     * Asymptotic complexity: O(n^3).
     *
     * Use [solveSystemBySeidelMethod] method to solve the strictly diagonally dominant system of linear equations.
     *
     * **See Also:** [https://en.wikipedia.org/wiki/Gauss–Seidel_method], [https://ru.wikipedia.org/wiki/Метод_Гаусса_—_Зейделя_решения_системы_линейных_уравнений]
     *
     * @param [inputA] is the input matrix of the system.
     * @param [inputB] is the input vector of the right side of the system.
     * @param [initialApproximation] is the input initial approximation array.
     * If the user does not pass their default values, then the following defaults will be used:
     * [inputB] vector divided on diagonal elements of the matrix [inputA].
     * Formula: 'Xi = Bi / Aii for i 0...n-1'
     * @param [eps] is the input required precision of the result.
     * The user can use, for example, 'eps = 0.001' if he need quickly solution with low error.
     * If the user does not pass their required precision, then will be used default machine precision as the most accurate precision.
     * @param [formSolution] is the flag, that says that the method need to form a solution object with the text of a detailed solution.
     *
     * @return This method returns approximate solution of the input system which is wrapped into [VectorResultWithStatus] object.
     * This object also contains solution of vector and array representation, successful flag, error-exception object if unsuccess, and solution object if needed.
     */
    fun solveSystemBySeidelMethod(
        inputA: Array<Array<Double>>,
        inputB: Array<Double>,
        initialApproximation: Array<Double>? = null,
        eps: Double? = null,
        formSolution: Boolean = false
    ): VectorResultWithStatus {
        return try {
            runSolvingSystemBySeidelMethod(
                inputA,
                inputB,
                initialApproximation,
                eps ?: getMachineEps(),
                formSolution
            )
        } catch (e: Exception) {
            VectorResultWithStatus(null, null, false, e, null)
        }
    }

    /**
     * Seidel method implementation.
     *
     * In numerical linear algebra, the Gauss–Seidel method, also known as the Liebmann method or the method of successive displacement,
     * is an iterative method used to solve a system of linear equations and is similar to the [JacobiMethod].
     * The Gauss-Seidel method can be considered as a modification of the Jacobi method, which, as practice shows,
     * requires approximately half the number of iterations compared to the Jacobi method.
     * Though it can be applied to any matrix with non-zero elements on the diagonals,
     * convergence is only guaranteed if the matrix is either strictly diagonally dominant, or symmetric and positive definite.
     *
     * Asymptotic complexity: O(n^3).
     *
     * Use [solveSystemBySeidelMethod] method to solve the strictly diagonally dominant system of linear equations.
     *
     * **See Also:** [https://en.wikipedia.org/wiki/Gauss–Seidel_method], [https://ru.wikipedia.org/wiki/Метод_Гаусса_—_Зейделя_решения_системы_линейных_уравнений]
     *
     * @param [inputA] is the input matrix of the system.
     * @param [inputB] is the input vector of the right side of the system.
     * @param [initialApproximation] is the input initial approximation array.
     * If the user does not pass their default values, then the following defaults will be used:
     * [inputB] vector divided on diagonal elements of the matrix [inputA].
     * Formula: 'Xi = Bi / Aii for i 0...n-1'
     * @param [eps] is the input required precision of the result.
     * The user can use, for example, 'eps = 0.001' if he need quickly solution with low error.
     * If the user does not pass their required precision, then will be used default machine precision as the most accurate precision.
     * @param [formSolution] is the flag, that says that the method need to form a solution object with the text of a detailed solution.
     *
     * @return This method returns approximate solution of the input system which is wrapped into [VectorResultWithStatus] object.
     * This object also contains solution of vector and array representation, successful flag, error-exception object if unsuccess, and solution object if needed.
     */
    fun solveSystemBySeidelMethod(
        inputA: Matrix,
        inputB: Vector,
        initialApproximation: Vector? = null,
        eps: Double? = null,
        formSolution: Boolean = false
    ): VectorResultWithStatus {
        return try {
            runSolvingSystemBySeidelMethod(
                inputA.getElems(),
                inputB.getElems(),
                initialApproximation?.getElems(),
                eps ?: getMachineEps(),
                formSolution
            )
        } catch (e: Exception) {
            VectorResultWithStatus(null, null, false, e, null)
        }
    }

    @Throws(IllegalArgumentException::class)
    private fun runSolvingSystemBySeidelMethod(
        inputA: Array<Array<Double>>,
        inputB: Array<Double>,
        initialApproximation: Array<Double>?,
        eps: Double,
        formSolution: Boolean
    ): VectorResultWithStatus {

        var solutionString: String = ""
        val solution: SeidelMethodSolution = SeidelMethodSolution()

        if (formSolution) solutionString += "The fully system solving solution of the Gauss-Seidel iterative method.\nChecking the dimensions of the input matrix and vector...\n"

        // Validation of the matrix and vector sizes
        if (inputA.size != inputA[0].size) {
            throw IllegalArgumentException("The size of 'inputA' matrix must be square (the number of rows must match the number of columns).")
        } else if (inputA.size != inputB.size) {
            throw IllegalArgumentException("The size of 'inputA' matrix must match the size of 'inputB' vector (the number of matrix rows must match the number of vector rows).")
        } else if ((initialApproximation != null) && (inputB.size != initialApproximation.size)) {
            throw IllegalArgumentException("The size of 'inputB' vector must match the size of 'initialApproximation' vector (the number of rows must be the same).")
        }

        if (formSolution) solutionString += "The dimensions of the input data correspond to each other.\n"

        // Check the sufficient condition of the convergence of the Seidel method: must be diagonal dominance of the matrix inputA
        if (formSolution) solutionString += "Checking the sufficient condition for the convergence of the Seidel method:\nneeded diagonal dominance of the input matrix A...\n"
        for (i in inputA.indices) {
            var sumOfRowWithoutDiagElem: Double = 0.0
            var diagElemAbs: Double = 0.0
            for (j in inputA[i].indices) {
                if (i != j) {
                    sumOfRowWithoutDiagElem += abs(inputA[i][j])
                } else {
                    diagElemAbs = abs(inputA[i][j])
                }
            }
            if (sumOfRowWithoutDiagElem >= diagElemAbs) {
                throw IllegalArgumentException("The sufficient condition for the convergence of the Seidel method is not satisfied: there is no diagonal dominance of the matrix inputA.")
            }
        }
        if (formSolution) solutionString += "Sufficient condition is satisfied.\n"

        val A: Array<Array<Double>> = inputA.map { it.clone() }.toTypedArray()
        val B: Array<Double> = inputB.clone()
        val X: Array<Double> = initialApproximation ?: getDefaultInitialApproximation(inputA, inputB)
        val n: Int = A.size

        if (formSolution) solutionString += "Input values of the system matrix A are: ${
            getPretty2DDoubleArrayString(
                A
            )
        }.\n"
        if (formSolution) solutionString += "The dimension of the system is ${n}x${n}.\n"
        if (formSolution) solutionString += "The vector B of the right-hand side of the equations values are: ${
            getPretty1DDoubleArrayString(
                B
            )
        }.\n"
        if (formSolution) solutionString += "Initial approximation vector values are: ${
            getPretty1DDoubleArrayString(
                X
            )
        }\n"
        if (formSolution) solutionString += "The Seidel algorithm will run until precision eps = ${eps} is reached.\n"

        var iterationsCounter = 0
        var xTmp: Array<Double> = Array(n) { 0.0 }
        var norm: Double
        do {
            if (formSolution) solutionString += "The new vector of the approximate solution will be recalculated until the norm is still smaller than eps.\n"
            for (i in 0 until n) {
                var sum: Double = B[i] // b_n
                for (j in 0 until n) {
                    if (j != i) {
                        sum -= A[i][j] * X[j]
                    }
                }
                // Update xi to use in the next row calculation
                X[i] = sum / A[i][i]

                if (formSolution) solutionString += "Calculated X${i} = ${xTmp[i]} on the ${iterationsCounter + 1} iteration.\n"
            }

            norm = calcNorm(X, xTmp)
            if (formSolution) solutionString += "Calculated norm value = ${norm}.\n"

            xTmp = X.clone()
            iterationsCounter++

        } while (norm > eps)

        if (formSolution) {
            solutionString += "The norm is smaller than eps, required precision has achieved on the ${iterationsCounter} iteration.\n"
            solutionString += "The approximate solution vector is ${getPretty1DDoubleArrayString(X)}.\n"
            solution.solutionString = solutionString
        }

        return VectorResultWithStatus(
            Vector(X),
            X,
            isSuccessful = true,
            errorException = null,
            solutionObject = if (formSolution) solution else null
        )
    }

    private fun calcNorm(x1: Array<Double>, x2: Array<Double>): Double {
        var sum = 0.0
        val n: Int = x1.size
        for (i in 0 until n) {
            sum += abs(x1[i] - x2[i])
        }
        return sum
    }

    private fun getDefaultInitialApproximation(A: Array<Array<Double>>, B: Array<Double>): Array<Double> {
        val n: Int = A.size
        val X: Array<Double> = Array(n) { 0.0 }
        // Initial approximation
        for (i in 0 until n) {
            X[i] = B[i] / A[i][i]
        }
        return X
    }

}