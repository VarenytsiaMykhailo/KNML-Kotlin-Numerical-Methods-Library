package com.github.varenytsiamykhailo.knml.systemsolvingmethods

import com.github.varenytsiamykhailo.knml.systemsolvingmethods.solutions.ThomasMethodSolution
import com.github.varenytsiamykhailo.knml.util.*
import com.github.varenytsiamykhailo.knml.util.results.VectorResultWithStatus

/**
 * Thomas method (tridiagonal matrix algorithm) implementation.
 *
 * In numerical linear algebra, the tridiagonal matrix algorithm, also known as the Thomas algorithm (named after Llewellyn Thomas),
 * is a simplified form of Gaussian elimination that can be used to solve tridiagonal systems of equations.
 * This method is based on forward and backward sweeps.
 *
 * Tridiagonal matrix is a band matrix that has nonzero elements on the main diagonal, the first diagonal below this, and the first diagonal above the main diagonal only.
 *
 * Asymptotic complexity: O(n) or O(n^2). O(n^2) is default, because by default this method implementation does validation of input matrix A, which must be tridiagonal.
 * You can get the required original asymptotic complexity O(n) by disabling validation, if you need to increase performance:
 * you can use 'increasePerformanceByIgnoringInputDataChecking' flag by true, and validation will be disabled.
 * But you yourself have to take care of the correctness of the input data.
 *
 * Use [solveSystemByThomasMethod] method to solve the tridiagonal system of linear equations.
 *
 * **See Also:** [https://en.wikipedia.org/wiki/Tridiagonal_matrix_algorithm], [https://ru.wikipedia.org/wiki/Метод_прогонки]
 */
class ThomasMethod {

    /**
     * Thomas method (tridiagonal matrix algorithm) implementation.
     *
     * In numerical linear algebra, the tridiagonal matrix algorithm, also known as the Thomas algorithm (named after Llewellyn Thomas),
     * is a simplified form of Gaussian elimination that can be used to solve tridiagonal systems of equations.
     * This method is based on forward and backward sweeps.
     *
     * Tridiagonal matrix is a band matrix that has nonzero elements on the main diagonal, the first diagonal below this, and the first diagonal above the main diagonal only.
     *
     * Asymptotic complexity: O(n) or O(n^2). O(n^2) is default, because by default this method implementation does validation of input matrix A, which must be tridiagonal.
     * You can get the required original asymptotic complexity O(n) by disabling validation, if you need to increase performance:
     * you can use 'increasePerformanceByIgnoringInputDataChecking' flag by true, and validation will be disabled.
     * But you yourself have to take care of the correctness of the input data.
     *
     * **See Also:** [https://en.wikipedia.org/wiki/Tridiagonal_matrix_algorithm], [https://ru.wikipedia.org/wiki/Метод_прогонки]
     *
     * @param [inputA] is the input matrix of the system.
     * @param [inputB] is the input vector of the right side of the system.
     * @param [formSolution] is the flag, that says that the method need to form a solution object with the text of a detailed solution.
     * @param [increasePerformanceByIgnoringInputDataChecking] is the flag, that says that the method will ignore validation of input matrix A, which must be tridiagonal. If true, asymptotic complexity will be O(n).
     *
     * @return This method returns solution of the input system which is wrapped into [VectorResultWithStatus] object.
     * This object also contains solution of vector and array representation, successful flag, error-exception object if unsuccess, and solution object if needed.
     */
    fun solveSystemByThomasMethod(
        inputA: Array<Array<Double>>,
        inputB: Array<Double>,
        formSolution: Boolean = false,
        increasePerformanceByIgnoringInputDataChecking: Boolean = false
    ): VectorResultWithStatus {
        return try {
            runSolvingSystemByThomasMethod(
                inputA,
                inputB,
                formSolution,
                increasePerformanceByIgnoringInputDataChecking
            )
        } catch (e: Exception) {
            VectorResultWithStatus(null, null, false, e, null)
        }
    }

    /**
     * Thomas method (tridiagonal matrix algorithm) implementation.
     *
     * In numerical linear algebra, the tridiagonal matrix algorithm, also known as the Thomas algorithm (named after Llewellyn Thomas),
     * is a simplified form of Gaussian elimination that can be used to solve tridiagonal systems of equations.
     * This method is based on forward and backward sweeps.
     *
     * Tridiagonal matrix is a band matrix that has nonzero elements on the main diagonal, the first diagonal below this, and the first diagonal above the main diagonal only.
     *
     * Asymptotic complexity: O(n) or O(n^2). O(n^2) is default, because by default this method implementation does validation of input matrix A, which must be tridiagonal.
     * You can get the required original asymptotic complexity O(n) by disabling validation, if you need to increase performance:
     * you can use 'increasePerformanceByIgnoringInputDataChecking' flag by true, and validation will be disabled.
     * But you yourself have to take care of the correctness of the input data.
     *
     * **See Also:** [https://en.wikipedia.org/wiki/Tridiagonal_matrix_algorithm], [https://ru.wikipedia.org/wiki/Метод_прогонки]
     *
     * @param [inputA] is the input matrix of the system.
     * @param [inputB] is the input vector of the right side of the system.
     * @param [formSolution] is the flag, that says that the method need to form a solution object with the text of a detailed solution.
     * @param [increasePerformanceByIgnoringInputDataChecking] is the flag, that says that the method will ignore validation of input matrix A, which must be tridiagonal. If true, asymptotic complexity will be O(n).
     *
     * @return This method returns solution of the input system which is wrapped into [VectorResultWithStatus] object.
     * This object also contains solution of vector and array representation, successful flag, error-exception object if unsuccess, and solution object if needed.
     */
    fun solveSystemByThomasMethod(
        inputA: Matrix,
        inputB: Vector,
        formSolution: Boolean = false,
        increasePerformanceByIgnoringInputDataChecking: Boolean = false
    ): VectorResultWithStatus {
        return try {
            runSolvingSystemByThomasMethod(
                inputA.getElems(),
                inputB.getElems(),
                formSolution,
                increasePerformanceByIgnoringInputDataChecking
            )
        } catch (e: Exception) {
            VectorResultWithStatus(null, null, false, e, null)
        }
    }

    @Throws(IllegalArgumentException::class)
    private fun runSolvingSystemByThomasMethod(
        inputA: Array<Array<Double>>,
        inputB: Array<Double>,
        formSolution: Boolean,
        increasePerformanceByIgnoringInputDataChecking: Boolean = false
    ): VectorResultWithStatus {

        var solutionString: String = ""
        val solution: ThomasMethodSolution = ThomasMethodSolution()

        if (formSolution) solutionString += "The fully system solving solution of the Thomas method.\nChecking the dimensions of the input matrix and vector...\n"

        // Validation of the matrix and vector sizes
        if (inputA.size != inputA[0].size) {
            throw IllegalArgumentException("The size of 'inputA' matrix must be square (the number of rows must match the number of columns).")
        } else if (inputA.size != inputB.size) {
            throw IllegalArgumentException("The size of 'inputA' matrix must match the 'inputB' vector (the number of matrix rows must match the number of vector rows).")
        }

        if (formSolution) solutionString += "The dimensions of the input data correspond to each other.\n"

        // Check the condition of the convergence of the Thomas method: the input matrix 'inputA' must be tridiagonal matrix: all elements of the matrix must be zero, except for the elements of the main diagonal and adjacent to it.
        if (!increasePerformanceByIgnoringInputDataChecking) {
            if (formSolution) solutionString += "Checking the sufficient condition for the convergence of the Thomas method:\nthe input matrix A must be tridiagonal matrix...\n"

            var isIncorrectData: Boolean = false
            loop@ for (i in inputA.indices) {
                for (j in inputA[i].indices) {
                    if (i == 0) {
                        if ((j != 0) && (j != 1)) {
                            if (inputA[i][j] != 0.0) {
                                isIncorrectData = true
                                break@loop
                            }
                        }
                    } else if (i == inputA.size - 1) {
                        if ((j != inputA[i].size - 2) && (j != inputA[i].size - 1)) {
                            if (inputA[i][j] != 0.0) {
                                isIncorrectData = true
                                break@loop
                            }
                        }
                    } else {
                        if ((j != i - 1) && (j != i) && (j != i + 1)) {
                            if (inputA[i][j] != 0.0) {
                                isIncorrectData = true
                                break@loop
                            }
                        }
                    }
                }
            }
            if (isIncorrectData) {
                throw IllegalArgumentException("The input matrix 'inputA' must be tridiagonal matrix: all elements of the matrix must be zero, except for the elements of the main diagonal and adjacent to it.")
            }

            if (formSolution) solutionString += "Sufficient condition is satisfied.\n"
        }

        val A: Array<Array<Double>> = inputA.map { it.clone() }.toTypedArray()
        val B: Array<Double> = inputB.clone()
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
        if (formSolution) solutionString += "The Thomas's algorithm will perform forward and backward sweeps.\n"

        // Storage for the coefficients "alpha", "betta" for each of the lines
        val alphaBettaCoefficients: Array<Array<Double>> = Array(n) { Array(2) { 0.0 } }

        // Forward sweep
        if (formSolution) solutionString += "Starting a forward sweep...\n"
        for (i in A.indices) {
            var y: Double
            var alpha: Double
            var betta: Double
            if (i == 0) {
                y = A[i][i]
                alpha = -A[i][i + 1] / y
                betta = B[i] / y
            } else {
                y = A[i][i] + A[i][i - 1] * alphaBettaCoefficients[i - 1][0]
                alpha = if (i != A.size - 1) { // For the last line, alpha is not needed
                    -A[i][i + 1] / y
                } else {
                    0.0
                }
                betta = (B[i] - A[i][i - 1] * alphaBettaCoefficients[i - 1][1]) / y
            }
            alphaBettaCoefficients[i][0] = alpha
            alphaBettaCoefficients[i][1] = betta
        }

        if (formSolution) {
            solutionString += "Calculated alpha and betta coefficients for each matrix line are:\n"
            for (i in alphaBettaCoefficients.indices) {
                solutionString += "For the line ${i + 1} alpha coefficient = ${alphaBettaCoefficients[i][0]}, betta coefficient = ${alphaBettaCoefficients[i][1]}.\n"
            }
        }


        // Backward sweep
        if (formSolution) solutionString += "Starting a backward sweep...\n"
        val ans: Array<Double> = Array(n) { 0.0 }

        for (i in A.indices.reversed()) {
            if (i == A.size - 1) {
                ans[i] = alphaBettaCoefficients[i][1]
            } else {
                ans[i] = alphaBettaCoefficients[i][0] * ans[i + 1] + alphaBettaCoefficients[i][1]
            }
        }
        if (formSolution)  {
            solutionString += "As a result of the backward sweep obtained solution vector:\n"
            solutionString += "The solution vector is ${getPretty1DDoubleArrayString(ans)}.\n"
            solution.solutionString = solutionString
        }

        return VectorResultWithStatus(
            Vector(ans),
            ans,
            isSuccessful = true,
            errorException = null,
            solutionObject = if (formSolution) solution else null
        )
    }
}