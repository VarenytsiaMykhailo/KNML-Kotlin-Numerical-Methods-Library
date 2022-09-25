package com.github.varenytsiamykhailo.knml.systemsolvingmethods

import com.github.varenytsiamykhailo.knml.util.Matrix
import com.github.varenytsiamykhailo.knml.util.Vector
import com.github.varenytsiamykhailo.knml.util.results.VectorResultWithStatus
import com.github.varenytsiamykhailo.knml.util.matrixMultiplicateVector
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import kotlin.math.abs
import kotlin.math.roundToInt

internal class GaussMethodTest {

    @Test
    fun test1SolveSystemByGaussClassicMethod() {
        val A: Array<Array<Double>> = arrayOf(
            arrayOf(4.0, 1.0, 0.0, 0.0),
            arrayOf(1.0, 4.0, 1.0, 0.0),
            arrayOf(0.0, 1.0, 4.0, 1.0),
            arrayOf(0.0, 0.0, 1.0, 4.0)
        )
        val B: Array<Double> = arrayOf(5.0, 6.0, 6.0, 5.0)

        val result: VectorResultWithStatus = GaussMethod().solveSystemByGaussClassicMethod(
            A,
            B,
            formSolution = true
        )

        assert(result.arrayResult != null)
        assert(result.arrayResult!!.size == 4)
        assert(result.arrayResult!![0].toString().startsWith("1.0"))
        assert(result.arrayResult!![1].toString().startsWith("1.0"))
        assert(result.arrayResult!![2].toString().startsWith("1.0"))
        assert(result.arrayResult!![3].toString().startsWith("1.0"))
        assert(result.vectorResult != null)
        assert(result.vectorResult!!.getN() == 4)
        assert(result.vectorResult!!.getElem(0).toString().startsWith("1.0"))
        assert(result.vectorResult!!.getElem(1).toString().startsWith("1.0"))
        assert(result.vectorResult!!.getElem(2).toString().startsWith("1.0"))
        assert(result.vectorResult!!.getElem(3).toString().startsWith("1.0"))
        assert(result.isSuccessful)
        assert(result.errorException == null)
        assert(result.solutionObject != null)
        assert(result.solutionObject!!.solutionString.length >= 10)

        val result2: Vector = matrixMultiplicateVector(Matrix(A), result.vectorResult!!)
        assertArrayEquals(
            B,
            result2.getElems()
        )
    }

    // Test that the method does not modify the input data
    @Test
    fun test2SolveSystemByGaussClassicMethod() {
        val A: Matrix = Matrix(
            arrayOf(
                arrayOf(4.0, 1.0, 0.0, 0.0),
                arrayOf(1.0, 4.0, 1.0, 0.0),
                arrayOf(0.0, 1.0, 4.0, 1.0),
                arrayOf(0.0, 0.0, 1.0, 4.0)
            )
        )
        val B: Vector = Vector(arrayOf(5.0, 6.0, 6.0, 5.0))

        val result: VectorResultWithStatus = GaussMethod().solveSystemByGaussClassicMethod(
            A.getElems(),
            B.getElems()
        )
        assert(result.arrayResult != null)
        assert(result.arrayResult!!.size == 4)
        assert(result.arrayResult!![0].toString() == "1.0")
        assert(result.arrayResult!![1] == 1.0)
        assert(result.arrayResult!![2] == 1.0)
        assert(result.arrayResult!![3] == 1.0)
        assert(result.vectorResult != null)
        assert(result.vectorResult!!.getN() == 4)
        assert(result.vectorResult!!.getElem(0) == 1.0)
        assert(result.vectorResult!!.getElem(1) == 1.0)
        assert(result.vectorResult!!.getElem(2) == 1.0)
        assert(result.vectorResult!!.getElem(3) == 1.0)
        assert(result.isSuccessful)
        assert(result.errorException == null)
        assert(result.solutionObject == null)

        assertArrayEquals(
            A.getElems(),
            arrayOf(
                arrayOf(4.0, 1.0, 0.0, 0.0),
                arrayOf(1.0, 4.0, 1.0, 0.0),
                arrayOf(0.0, 1.0, 4.0, 1.0),
                arrayOf(0.0, 0.0, 1.0, 4.0)
            )
        )
        assertArrayEquals(
            B.getElems(),
            arrayOf(5.0, 6.0, 6.0, 5.0)
        )
    }

    @Test
    fun test3SolveSystemByGaussClassicMethod() {
        val A: Matrix = Matrix(
            arrayOf(
                arrayOf(2.0, -1.0, 0.0),
                arrayOf(5.0, 4.0, 2.0),
                arrayOf(0.0, 1.0, -3.0)
            )
        )
        val B: Vector = Vector(arrayOf(3.0, 6.0, 2.0))

        val result: VectorResultWithStatus = ThomasMethod().solveSystemByThomasMethod(
            A,
            B
        )
        assert(result.arrayResult != null)
        assert(result.arrayResult!!.size == 3)
        assert(result.arrayResult!![0].toString().startsWith("1.488"))
        assert(result.arrayResult!![1].toString().startsWith("-0.0232"))
        assert(result.arrayResult!![2].toString().startsWith("-0.674"))
        assert(result.vectorResult != null)
        assert(result.vectorResult!!.getN() == 3)
        assert(result.vectorResult!!.getElem(0).toString().startsWith("1.488"))
        assert(result.vectorResult!!.getElem(1).toString().startsWith("-0.0232"))
        assert(result.vectorResult!!.getElem(2).toString().startsWith("-0.674"))
        assert(result.isSuccessful)
        assert(result.errorException == null)
        assert(result.solutionObject == null)

        val result2: Vector = matrixMultiplicateVector(A, result.vectorResult!!)
        assertArrayEquals(
            B.getElems(),
            result2.getElems()
        )
    }

    @Test
    fun test4SolveSystemByGaussClassicMethod() {
        val A: Matrix = Matrix(
            arrayOf(
                arrayOf(2.0, -1.0, 0.0),
                arrayOf(5.0, 4.0, 2.0),
                arrayOf(0.0, 1.0, -3.0)
            )
        )
        val B: Vector = Vector(arrayOf(3.0, 6.0, 2.0))

        val result: VectorResultWithStatus = GaussMethod().solveSystemByGaussClassicMethod(
            A,
            B
        )
        assert(result.arrayResult != null)
        assert(result.arrayResult!!.size == 3)
        assert(result.arrayResult!![0].toString().startsWith("1.488"))
        assert(result.arrayResult!![1].toString().startsWith("-0.0232"))
        assert(result.arrayResult!![2].toString().startsWith("-0.674"))
        assert(result.vectorResult != null)
        assert(result.vectorResult!!.getN() == 3)
        assert(result.vectorResult!!.getElem(0).toString().startsWith("1.488"))
        assert(result.vectorResult!!.getElem(1).toString().startsWith("-0.0232"))
        assert(result.vectorResult!!.getElem(2).toString().startsWith("-0.674"))
        assert(result.isSuccessful)
        assert(result.errorException == null)
        assert(result.solutionObject == null)

        val result2: Vector = matrixMultiplicateVector(A, result.vectorResult!!)
        assertArrayEquals(
            B.getElems(),
            result2.getElems()
        )
    }

    @Test
    fun test5SolveSystemByGaussClassicMethod() {
        val A: Array<Array<Double>> =  arrayOf(
            arrayOf(115.0, -20.0, -75.0),
            arrayOf(15.0, -50.0, -5.0),
            arrayOf(6.0, 2.0, 20.0)
        )
        val B: Array<Double> = arrayOf(20.0, -40.0, 28.0)

        val result: VectorResultWithStatus = GaussMethod().solveSystemByGaussClassicMethod(
            A,
            B,
            formSolution = true
        )

        assert(result.arrayResult != null)
        assert(result.arrayResult!!.size == 3)
        assert(result.arrayResult!![0].toString().startsWith("1.0"))
        assert(result.arrayResult!![1].toString().startsWith("1.0"))
        assert(result.arrayResult!![2].toString().startsWith("1.0"))
        assert(result.vectorResult != null)
        assert(result.vectorResult!!.getN() == 3)
        assert(result.vectorResult!!.getElem(0).toString().startsWith("1.0"))
        assert(result.vectorResult!!.getElem(1).toString().startsWith("1.0"))
        assert(result.vectorResult!!.getElem(2).toString().startsWith("1.0"))
        assert(result.isSuccessful)
        assert(result.errorException == null)
        assert(result.solutionObject != null)
        assert(result.solutionObject!!.solutionString.length >= 10)

        val result2: Vector = matrixMultiplicateVector(Matrix(A), result.vectorResult!!)
        for (i in result2.getElems().indices) {
            assert(abs(B[i] - result2.getElems()[i]) <= 0.5)
        }
    }

    @Test
    fun test6SolveSystemByGaussClassicMethod() {
        val A: Matrix = Matrix(
            arrayOf(
                arrayOf(20.9, 1.2, 2.1, 0.9),
                arrayOf(1.2, 21.2, 1.5, 2.5),
                arrayOf(2.1, 1.5, 19.8, 1.3),
                arrayOf(0.9, 2.5, 1.3, 32.1)
            )
        )
        val B: Vector = Vector(arrayOf(21.70, 27.46, 28.76, 49.72))

        val result: VectorResultWithStatus = GaussMethod().solveSystemByGaussClassicMethod(
            A,
            B
        )
        assert(result.arrayResult != null)
        assert(result.arrayResult!!.size == 4)
        assert(abs(result.arrayResult!![0] - 0.8) <= 0.1)
        assert(abs(result.arrayResult!![1] - 1.0) <= 0.1)
        assert(abs(result.arrayResult!![2] - 1.2) <= 0.1)
        assert(abs(result.arrayResult!![3] - 1.4) <= 0.1)
        assert(result.vectorResult != null)
        assert(result.vectorResult!!.getN() == 4)
        assert(result.vectorResult!!.getElem(3) == 1.4)
        assert(result.isSuccessful)
        assert(result.errorException == null)
        assert(result.solutionObject == null)

        val result2: Vector = matrixMultiplicateVector(A, result.vectorResult!!)
        for (i in result2.getElems().indices) {
            assert(abs(B.getElems()[i] - result2.getElems()[i]) <= 0.0001)
        }
    }

    /**
     * Exception test: matrix A must be square.
     */
    @Test
    fun test7SolveSystemByGaussClassicMethod() {
        val A: Matrix = Matrix(
            arrayOf(
                arrayOf(4.0, 1.0, 0.0, 0.0),
                arrayOf(1.0, 4.0, 1.0, 0.0),
                arrayOf(0.0, 1.0, 4.0, 1.0)
            )
        )
        val B: Vector = Vector(arrayOf(5.0, 6.0, 6.0, 5.0))

        val result: VectorResultWithStatus = GaussMethod().solveSystemByGaussClassicMethod(
            A,
            B,
            formSolution = true
        )
        assert(result.arrayResult == null)
        assert(result.vectorResult == null)
        assert(!result.isSuccessful)
        assert(result.errorException != null)
        assert(result.solutionObject == null)
    }

    /**
     * Exception test: dimension of matrix A bigger than matrix B.
     */
    @Test
    fun test8SolveSystemByGaussClassicMethod() {
        val A: Matrix = Matrix(
            arrayOf(
                arrayOf(4.0, 1.0, 0.0, 0.0),
                arrayOf(1.0, 4.0, 1.0, 0.0),
                arrayOf(0.0, 1.0, 4.0, 1.0),
                arrayOf(0.0, 0.0, 1.0, 4.0)
            )
        )
        val B: Vector = Vector(arrayOf(5.0, 6.0, 6.0))

        val result: VectorResultWithStatus = GaussMethod().solveSystemByGaussClassicMethod(
            A,
            B,
            formSolution = true
        )
        assert(result.arrayResult == null)
        assert(result.vectorResult == null)
        assert(!result.isSuccessful)
        assert(result.errorException != null)
        assert(result.solutionObject == null)
    }

    /**
     * Exception test: dimension of matrix A smaller than matrix B.
     */
    @Test
    fun test9SolveSystemByGaussClassicMethod() {
        val A: Matrix = Matrix(
            arrayOf(
                arrayOf(4.0, 1.0, 0.0, 0.0),
                arrayOf(1.0, 4.0, 1.0, 0.0),
                arrayOf(0.0, 1.0, 4.0, 1.0),
                arrayOf(0.0, 0.0, 1.0, 4.0)
            )
        )
        val B: Vector = Vector(arrayOf(5.0, 6.0, 6.0, 5.0, 123.1239999999))

        val result: VectorResultWithStatus = ThomasMethod().solveSystemByThomasMethod(
            A,
            B,
            formSolution = true
        )
        assert(result.arrayResult == null)
        assert(result.vectorResult == null)
        assert(!result.isSuccessful)
        assert(result.errorException != null)
        assert(result.solutionObject == null)
    }

    @Test
    fun test10SolveSystemByGaussClassicMethod() {
        val A: Matrix = Matrix(
            arrayOf(
                arrayOf(2.0, -1.0, 0.0),
                arrayOf(5.0, 4.0, 2.0),
                arrayOf(0.0, 1.0, -3.0)
            )
        )
        val X: Vector = Vector(arrayOf(1.488, -0.023, -0.674))

        val resultB: Vector = matrixMultiplicateVector(A, X)

        val resultX: VectorResultWithStatus = GaussMethod().solveSystemByGaussClassicMethod(
            A,
            resultB
        )

        for (i in 0 until resultX.vectorResult!!.getElems().size) {
            resultX.vectorResult!!.setElem(i, (resultX.vectorResult!!.getElem(i) * 1000).roundToInt() / 1000.0)
        }
        assert(X == resultX.vectorResult)
    }

    @Test
    fun test11SolveSystemByGaussClassicMethodWithPartialPivoting() {
        val A: Matrix = Matrix(
            arrayOf(
                arrayOf(2.0, -1.0, 0.0),
                arrayOf(5.0, 4.0, 2.0),
                arrayOf(0.0, 1.0, -3.0)
            )
        )
        val B: Vector = Vector(arrayOf(3.0, 6.0, 2.0))

        val resultWithPivotingByRow: VectorResultWithStatus = GaussMethod().solveSystemByGaussMethodWithPivoting(
            A,
            B,
            true,
            GaussMethod.Companion.PivotingStrategy.PartialByRow
        )
        assert(resultWithPivotingByRow.arrayResult != null)
        assert(resultWithPivotingByRow.arrayResult!!.size == 3)
        assert(resultWithPivotingByRow.arrayResult!![0].toString().startsWith("1.488"))
        assert(resultWithPivotingByRow.arrayResult!![1].toString().startsWith("-0.0232"))
        assert(resultWithPivotingByRow.arrayResult!![2].toString().startsWith("-0.674"))
        assert(resultWithPivotingByRow.vectorResult != null)
        assert(resultWithPivotingByRow.vectorResult!!.getN() == 3)
        assert(resultWithPivotingByRow.vectorResult!!.getElem(0).toString().startsWith("1.488"))
        assert(resultWithPivotingByRow.vectorResult!!.getElem(1).toString().startsWith("-0.0232"))
        assert(resultWithPivotingByRow.vectorResult!!.getElem(2).toString().startsWith("-0.674"))
        assert(resultWithPivotingByRow.isSuccessful)
        assert(resultWithPivotingByRow.errorException == null)

        val resultWithPivotingByColumn: VectorResultWithStatus = GaussMethod().solveSystemByGaussMethodWithPivoting(
            A,
            B,
            true,
            GaussMethod.Companion.PivotingStrategy.PartialByColumn
        )
        assert(resultWithPivotingByRow.arrayResult.contentEquals(resultWithPivotingByColumn.arrayResult))

        val resultWithCompletePivoting: VectorResultWithStatus = GaussMethod().solveSystemByGaussMethodWithPivoting(
            A,
            B,
            true,
            GaussMethod.Companion.PivotingStrategy.Complete
        )
        assert(resultWithPivotingByRow.arrayResult.contentEquals(resultWithCompletePivoting.arrayResult))
    }
}