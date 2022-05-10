package com.github.varenytsiamykhailo.knml.systemsolvingmethods

import com.github.varenytsiamykhailo.knml.util.Matrix
import com.github.varenytsiamykhailo.knml.util.Vector
import com.github.varenytsiamykhailo.knml.util.results.VectorResultWithStatus
import com.github.varenytsiamykhailo.knml.util.matrixMultiplicateVector
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ThomasMethodTest {

    @Test
    fun test1SolveSystemByThomasMethod() {
        val A: Array<Array<Double>> = arrayOf(
            arrayOf(4.0, 1.0, 0.0, 0.0),
            arrayOf(1.0, 4.0, 1.0, 0.0),
            arrayOf(0.0, 1.0, 4.0, 1.0),
            arrayOf(0.0, 0.0, 1.0, 4.0)
        )
        val B: Array<Double> = arrayOf(5.0, 6.0, 6.0, 5.0)

        val result: VectorResultWithStatus = ThomasMethod().solveSystemByThomasMethod(
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
    fun test2SolveSystemByThomasMethod() {
        val A: Matrix = Matrix(
            arrayOf(
                arrayOf(4.0, 1.0, 0.0, 0.0),
                arrayOf(1.0, 4.0, 1.0, 0.0),
                arrayOf(0.0, 1.0, 4.0, 1.0),
                arrayOf(0.0, 0.0, 1.0, 4.0)
            )
        )
        val B: Vector = Vector(arrayOf(5.0, 6.0, 6.0, 5.0))

        val result: VectorResultWithStatus = JacobiMethod().solveSystemByJacobiMethod(
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
    fun test3SolveSystemByThomasMethod() {
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

    /**
     * Exception test: matrix A must be square.
     */
    @Test
    fun test4SolveSystemByThomasMethod() {
        val A: Matrix = Matrix(
            arrayOf(
                arrayOf(4.0, 1.0, 0.0, 0.0),
                arrayOf(1.0, 4.0, 1.0, 0.0),
                arrayOf(0.0, 1.0, 4.0, 1.0)
            )
        )
        val B: Vector = Vector(arrayOf(5.0, 6.0, 6.0, 5.0))

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

    /**
     * Exception test: dimension of matrix A bigger than matrix B.
     */
    @Test
    fun test5SolveSystemByThomasMethod() {
        val A: Matrix = Matrix(
            arrayOf(
                arrayOf(4.0, 1.0, 0.0, 0.0),
                arrayOf(1.0, 4.0, 1.0, 0.0),
                arrayOf(0.0, 1.0, 4.0, 1.0),
                arrayOf(0.0, 0.0, 1.0, 4.0)
            )
        )
        val B: Vector = Vector(arrayOf(5.0, 6.0, 6.0))

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

    /**
     * Exception test: dimension of matrix A smaller than matrix B.
     */
    @Test
    fun test6SolveSystemByThomasMethod() {
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

    /**
     * Exception test: the sufficient condition for the convergence of the Thomas method is not satisfied:
     * the matrix inputA must be tridiagonal.
     */
    @Test
    fun test7SolveSystemByThomasMethod() {
        val A: Matrix = Matrix(
            arrayOf(
                arrayOf(4.0, 1.0, 0.0, 0.0),
                arrayOf(1.0, 4.0, 1.0, 0.0),
                arrayOf(0.0, 1.0, 4.0, 1.0),
                arrayOf(9999999999999.0, 0.0, 1.0, 4.0)
            )
        )
        val B: Vector = Vector(arrayOf(5.0, 6.0, 6.0, 5.0))

        val result: VectorResultWithStatus = ThomasMethod().solveSystemByThomasMethod(
            A,
            B,
            formSolution = true,
            increasePerformanceByIgnoringInputDataChecking = false
        )
        assert(result.arrayResult == null)
        assert(result.vectorResult == null)
        assert(!result.isSuccessful)
        assert(result.errorException != null)
        assert(result.solutionObject == null)
    }
}