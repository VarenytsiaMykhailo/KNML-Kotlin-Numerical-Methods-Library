package com.github.varenytsiamykhailo.knml.systemsolvingmethods

import com.github.varenytsiamykhailo.knml.systemsolvingmethods.solutions.GaussClassicMethodSolution
import com.github.varenytsiamykhailo.knml.util.*
import com.github.varenytsiamykhailo.knml.util.results.VectorResultWithStatus
import kotlin.math.abs
import kotlin.math.max

/**
 * Gauss method implementation.
 *
 * In mathematics, Gaussian elimination, also known as row reduction, is an algorithm for solving systems of linear equations.
 * It consists of a sequence of operations performed on the corresponding matrix of coefficients.
 * This is a method of successive elimination of variables, when, with the help of elementary transformations,
 * the system of equations is reduced to an equivalent system of a triangular type,
 * from which all the variables of the system are found sequentially, starting from the last (by number).
 * This method is based on forward and backward sweeps.
 *
 * Asymptotic complexity: O(n^3).
 *
 * Use [solveSystemByGaussClassicMethod] method to solve the system of linear equations.
 * This method provides a classic implementation, without rows or columns with the maximum elements swapping.
 *
 * **See Also:** [https://en.wikipedia.org/wiki/Gaussian_elimination], [https://ru.wikipedia.org/wiki/Метод_Гаусса]
 */
class GaussMethod {

    companion object {
        private val EPSILON: Double = 1e-10
    }

    enum class PivotingStrategy {
        PartialByRow,
        PartialByColumn,
        Complete
    }

    /**
     * Gauss method implementation.
     *
     * In mathematics, Gaussian elimination, also known as row reduction, is an algorithm for solving systems of linear equations.
     * It consists of a sequence of operations performed on the corresponding matrix of coefficients.
     * This is a method of successive elimination of variables, when, with the help of elementary transformations,
     * the system of equations is reduced to an equivalent system of a triangular type,
     * from which all the variables of the system are found sequentially, starting from the last (by number).
     * This method is based on forward and backward sweeps.
     *
     * Asymptotic complexity: O(n^3).
     *
     * Use [solveSystemByGaussClassicMethod] method to solve the system of linear equations.
     * This method provides a classic implementation, without rows or columns with the maximum elements swapping.
     *
     * **See Also:** [https://en.wikipedia.org/wiki/Gaussian_elimination], [https://ru.wikipedia.org/wiki/Метод_Гаусса]
     *
     * @param [inputA] is the input matrix of the system.
     * @param [inputB] is the input vector of the right side of the system.
     * @param [formSolution] is the flag, that says that the method need to form a solution object with the text of a detailed solution.
     *
     * @return This method returns solution of the input system which is wrapped into [VectorResultWithStatus] object.
     * This object also contains solution of vector and array representation, successful flag, error-exception object if unsuccess, and solution object if needed.
     */
    fun solveSystemByGaussClassicMethod(
        inputA: Array<Array<Double>>,
        inputB: Array<Double>,
        formSolution: Boolean = false
    ): VectorResultWithStatus {
        return try {
            runSolvingSystemByGaussClassicMethod(
                inputA,
                inputB,
                formSolution
            )
        } catch (e: Exception) {
            VectorResultWithStatus(null, null, false, e, null)
        }
    }

    /**
     * Gauss method implementation.
     *
     * In mathematics, Gaussian elimination, also known as row reduction, is an algorithm for solving systems of linear equations.
     * It consists of a sequence of operations performed on the corresponding matrix of coefficients.
     * This is a method of successive elimination of variables, when, with the help of elementary transformations,
     * the system of equations is reduced to an equivalent system of a triangular type,
     * from which all the variables of the system are found sequentially, starting from the last (by number).
     * This method is based on forward and backward sweeps.
     *
     * Asymptotic complexity: O(n^3).
     *
     * Use [solveSystemByGaussClassicMethod] method to solve the system of linear equations.
     * This method provides a classic implementation, without rows or columns with the maximum elements swapping.
     *
     * **See Also:** [https://en.wikipedia.org/wiki/Gaussian_elimination], [https://ru.wikipedia.org/wiki/Метод_Гаусса]
     *
     * @param [inputA] is the input matrix of the system.
     * @param [inputB] is the input vector of the right side of the system.
     * @param [formSolution] is the flag, that says that the method need to form a solution object with the text of a detailed solution.
     *
     * @return This method returns solution of the input system which is wrapped into [VectorResultWithStatus] object.
     * This object also contains solution of vector and array representation, successful flag, error-exception object if unsuccess, and solution object if needed.
     */
    fun solveSystemByGaussClassicMethod(
        inputA: Matrix,
        inputB: Vector,
        formSolution: Boolean = false
    ): VectorResultWithStatus {
        return try {
            runSolvingSystemByGaussClassicMethod(
                inputA.getElems(),
                inputB.getElems(),
                formSolution
            )
        } catch (e: Exception) {
            VectorResultWithStatus(null, null, false, e, null)
        }
    }

    /**
     * Gauss method with choice of pivot element strategy implementation.
     *
     * In mathematics, Gaussian elimination, also known as row reduction, is an algorithm for solving systems of linear equations.
     * It consists of a sequence of operations performed on the corresponding matrix of coefficients.
     * This is a method of successive elimination of variables, when, with the help of elementary transformations,
     * the system of equations is reduced to an equivalent system of a triangular type,
     * from which all the variables of the system are found sequentially, starting from the last (by number).
     * This method is based on forward and backward sweeps.
     *
     * Asymptotic complexity: O(n^3).
     *
     * Use [solveSystemByGaussClassicMethod] method to solve the system of linear equations.
     * This method provides a classic implementation, without rows or columns with the maximum elements swapping.
     *
     * **See Also:** [https://en.wikipedia.org/wiki/Gaussian_elimination], [https://ru.wikipedia.org/wiki/Метод_Гаусса]
     *
     * @param [inputA] is the input matrix of the system.
     * @param [inputB] is the input vector of the right side of the system.
     * @param [formSolution] is the flag, that says that the method need to form a solution object with the text of a detailed solution.
     * @param [pivoting] is the type of the desired selection of pivoting strategy.
     *
     * @return This method returns solution of the input system which is wrapped into [VectorResultWithStatus] object.
     * This object also contains solution of vector and array representation, successful flag, error-exception object if unsuccess, and solution object if needed.
     */
    fun solveSystemByGaussMethodWithPivoting(
        inputA: Matrix,
        inputB: Vector,
        formSolution: Boolean = false,
        pivoting: PivotingStrategy
    ): VectorResultWithStatus {
        return try {
            runSolvingSystemByGaussMethodWithPivoting(
                inputA.getElems(),
                inputB.getElems(),
                formSolution,
                pivoting
            )
        } catch (e: Exception) {
            VectorResultWithStatus(null, null, false, e, null)
        }
    }

    /**
     * Gauss method with choice of pivot element strategy implementation.
     *
     * In mathematics, Gaussian elimination, also known as row reduction, is an algorithm for solving systems of linear equations.
     * It consists of a sequence of operations performed on the corresponding matrix of coefficients.
     * This is a method of successive elimination of variables, when, with the help of elementary transformations,
     * the system of equations is reduced to an equivalent system of a triangular type,
     * from which all the variables of the system are found sequentially, starting from the last (by number).
     * This method is based on forward and backward sweeps.
     *
     * Asymptotic complexity: O(n^3).
     *
     * Use [solveSystemByGaussClassicMethod] method to solve the system of linear equations.
     * This method provides a classic implementation, without rows or columns with the maximum elements swapping.
     *
     * **See Also:** [https://en.wikipedia.org/wiki/Gaussian_elimination], [https://ru.wikipedia.org/wiki/Метод_Гаусса]
     *
     * @param [inputA] is the input matrix of the system.
     * @param [inputB] is the input vector of the right side of the system.
     * @param [formSolution] is the flag, that says that the method need to form a solution object with the text of a detailed solution.
     * @param [pivoting] is the type of the desired selection of pivoting strategy.
     *
     * @return This method returns solution of the input system which is wrapped into [VectorResultWithStatus] object.
     * This object also contains solution of vector and array representation, successful flag, error-exception object if unsuccess, and solution object if needed.
     */
    fun solveSystemByGaussMethodWithPivoting(
        inputA: Array<Array<Double>>,
        inputB: Array<Double>,
        formSolution: Boolean = false,
        pivoting: PivotingStrategy
    ): VectorResultWithStatus {
        return try {
            runSolvingSystemByGaussMethodWithPivoting(
                inputA,
                inputB,
                formSolution,
                pivoting
            )
        } catch (e: Exception) {
            VectorResultWithStatus(null, null, false, e, null)
        }
    }

    @Throws(IllegalArgumentException::class)
    private fun runSolvingSystemByGaussClassicMethod(
        inputA: Array<Array<Double>>,
        inputB: Array<Double>,
        formSolution: Boolean
    ): VectorResultWithStatus {

        var solutionString: String = ""
        val solution: GaussClassicMethodSolution = GaussClassicMethodSolution()

        if (formSolution) solutionString += "The fully system solving solution of the Gauss classic method.\nChecking the dimensions of the input matrix and vector...\n"

        // Validation of the matrix and vector sizes
        if (inputA.size != inputA[0].size) {
            throw IllegalArgumentException("The size of 'inputA' matrix must be square (the number of rows must match the number of columns).")
        } else if (inputA.size != inputB.size) {
            throw IllegalArgumentException("The size of 'inputA' matrix must match the 'inputB' vector (the number of matrix rows must match the number of vector rows).")
        }

        if (formSolution) solutionString += "The dimensions of the input data correspond to each other.\n"

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
        if (formSolution) solutionString += "The Gauss's classic algorithm will perform forward and backward sweeps.\n"

        // matrix contains matrix A and vector B in one
        val matrix: Array<Array<Double>> = Array(n) { Array(n + 1) { 0.0 } }
        for (i in A.indices) {
            for (j in A[i].indices) {
                matrix[i][j] = A[i][j]
            }
        }
        for (i in B.indices) {
            matrix[i][n] = B[i]
        }

        val matrixClone: Array<Array<Double>> = matrix.map { it.clone() }.toTypedArray()


        // Forward sweep
        if (formSolution) solutionString += "Starting a forward sweep to bring the matrix to the upper triangular form using elementary matrix transformations...\n"
        for (k in 0 until n) { // k - number of row
            if (formSolution) solutionString += "Dividing a ${k}-row by the first term != 0 to convert it into 1.0.\n"
            for (i in 0 until n + 1) { // i - number of column
                matrixClone[k][i] =
                    matrixClone[k][i] / matrix[k][k] // Dividing a k-row by the first term != 0 to convert it into 1.0
            }

            if (formSolution) solutionString += "Zeroing matrix elements below the first term converted to 1.0...\n"
            for (i in k + 1 until n) { // i - next row number after k
                if (formSolution) solutionString += "For the ${i} row:\n"
                val C: Double = matrixClone[i][k] / matrixClone[k][k] // coefficient
                if (formSolution) solutionString += "Calculated coefficient by which we will multiply the ${k} row is ${matrixClone[i][k]}/${matrixClone[k][k]} = ${C}.\n"
                if (formSolution) solutionString += "Subtract the (${k} row multiplied coefficient ${C}) from the ${i} row.\n"
                for (j in 0 until n + 1) { // j - the column number of the next row after k
                    matrixClone[i][j] =
                        matrixClone[i][j] - matrixClone[k][j] * C // Zeroing matrix elements below the first term converted to 1.0

                }
            }

            // Update
            for (i in 0 until n) {
                for (j in 0 until n + 1) {
                    matrix[i][j] = matrixClone[i][j]
                }
            }
        }
        if (formSolution) solutionString += "As a result of a forward sweep received upper triangular matrix: ${
            getPretty2DDoubleArrayString(
                matrixClone
            )
        }.\n"


        // Backward sweep
        if (formSolution) solutionString += "Starting a backward sweep: calculation of the solution...\n"
        for (k in n - 1 downTo -1 + 1) { // k - number of row
            for (i in n downTo -1 + 1) { // i - number of column
                matrixClone[k][i] = matrixClone[k][i] / matrix[k][k]
            }
            for (i in k - 1 downTo -1 + 1) {  // i - next row number after k
                val C: Double = matrixClone[i][k] / matrixClone[k][k] // coefficient
                for (j in n downTo -1 + 1) { // j - the column number of the next row after k
                    matrixClone[i][j] = matrixClone[i][j] - matrixClone[k][j] * C
                }
            }
        }
        if (formSolution) solutionString += "As a result of a backward sweep received matrix: ${
            getPretty2DDoubleArrayString(
                matrixClone
            )
        }.\n"


        // Answer parsing
        val result: Array<Double> = Array(n) { 0.0 }
        for (i in 0 until n) {
            result[i] = matrixClone[i][n]
        }
        if (formSolution) {
            solutionString += "As a result of the backward sweep obtained solution vector: ${
                getPretty1DDoubleArrayString(
                    result
                )
            }.\n"
            solution.solutionString = solutionString
        }

        return VectorResultWithStatus(
            Vector(result),
            result,
            isSuccessful = true,
            errorException = null,
            solutionObject = if (formSolution) solution else null
        )
    }

    private fun runSolvingSystemByGaussMethodWithPivoting(
        inputA: Array<Array<Double>>,
        inputB: Array<Double>,
        formSolution: Boolean = false,
        pivoting: PivotingStrategy
    ): VectorResultWithStatus {
        var solutionString: String = ""
        val solution: GaussClassicMethodSolution = GaussClassicMethodSolution()

        if (formSolution) solutionString += "The fully system solving solution of the Gauss classic method.\nChecking the dimensions of the input matrix and vector...\n"

        // Validation of the matrix and vector sizes
        if (inputA.size != inputA[0].size) {
            throw IllegalArgumentException("The size of 'inputA' matrix must be square (the number of rows must match the number of columns).")
        } else if (inputA.size != inputB.size) {
            throw IllegalArgumentException("The size of 'inputA' matrix must match the 'inputB' vector (the number of matrix rows must match the number of vector rows).")
        }

        if (formSolution) solutionString += "The dimensions of the input data correspond to each other.\n"

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
        if (formSolution) solutionString += "The Gauss's classic algorithm will perform forward and backward sweeps.\n"

        when (pivoting) {
            PivotingStrategy.Complete -> {
                if (formSolution) solutionString += "Version of the algorithm of Gaussian elimination with complete pivoting," +
                        " in which the absolute value of the pivot is maximized not only by exchanging rows, " +
                        "but also by exchanging columns (i.e., by changing the order of the unknowns).\n" +
                        "We search all the quadrant of the coefficient matrix below and to the right of the pivotal position" +
                        " for the element that has the largest absolute value and then we move that element to the pivotal " +
                        "position with a row and a column interchange.\n";
            }
            PivotingStrategy.PartialByColumn,
            PivotingStrategy.PartialByRow -> {
                if (formSolution) solutionString += "The partial pivoting technique is used to avoid roundoff errors " +
                        "that could be caused when dividing every entry of a row by a pivot value that is relatively " +
                        "small in comparison to its remaining row entries.\n\nIn partial pivoting, for each new pivot" +
                        " column in turn, check whether there is an entry having a greater absolute value in that " +
                        "column below the current pivot row. If so, choose the entry among these having the maximum " +
                        "absolute value. (If two or more entries have the maximum absolute value, choose any one of " +
                        "those.) Then we switch rows to place the chosen entry into the pivot position before " +
                        "continuing the row reduction process."
            }
        }
        if (formSolution) solutionString += "Find pivot row and swap.\n" // Find pivot row and swap - находим опорную строку и меняем местами
        for (p in 0 until n) {
            var max = p
            if (pivoting == PivotingStrategy.Complete) {
                var maxInRow = p
                var maxInColumn = p
                for (i in p + 1 until n) {
                    if (abs(inputA[i][p]) > abs(inputA[max][p])) {
                        maxInRow = i
                    }
                    if (abs(inputA[p][i]) > abs(inputA[p][max])) {
                        maxInColumn = i
                    }
                    max = max(maxInRow, maxInColumn)
                }
            } else {
                for (i in p + 1 until n) {
                    val swapCondition =
                        when (pivoting) {
                            PivotingStrategy.PartialByRow -> abs(inputA[i][p]) > abs(inputA[max][p])
                            PivotingStrategy.PartialByColumn -> abs(inputA[p][i]) > abs(inputA[p][max])
                            else -> false
                        }
                    if (swapCondition) {
                        max = i
                    }
                }
            }

            val temp: Array<Double> = inputA[p]
            inputA[p] = inputA[max]
            inputA[max] = temp

            val t = inputB[p]
            inputB[p] = inputB[max]
            inputB[max] = t

            // Singular or nearly singular
            if (abs(inputA[p][p]) <= EPSILON) {
                throw ArithmeticException("Matrix is singular or nearly singular")
            }

            // Pivot within inputA and inputB
            for (i in p + 1 until n) {
                val alpha: Double = inputA[i][p] / inputA[p][p]
                inputB[i] -= alpha * inputB[p]
                for (j in p until n) {
                    inputA[i][j] -= alpha * inputA[p][j]
                }
            }
        }

        // Back substitution
        if (formSolution) solutionString += "Starting a backward sweep: calculation of the solution...\n"
        val result: Array<Double> = Array(n) { 0.0 }
        for (i in n - 1 downTo 0) {
            var sum: Double = 0.0
            for (j in i + 1 until n) {
                sum += inputA[i][j] * result[j]
            }
            result[i] = (inputB[i] - sum) / inputA[i][i]
        }

        if (formSolution) {
            solutionString += "As a result of the backward sweep obtained solution vector: ${
                getPretty1DDoubleArrayString(
                    result
                )
            }.\n"
            solution.solutionString = solutionString
        }

        return VectorResultWithStatus(
            Vector(result),
            result,
            isSuccessful = true,
            errorException = null,
            solutionObject = if (formSolution) solution else null
        )
    }
}