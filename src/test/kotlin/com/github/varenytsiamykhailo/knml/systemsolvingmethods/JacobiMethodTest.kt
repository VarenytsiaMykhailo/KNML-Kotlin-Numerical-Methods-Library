package com.github.varenytsiamykhailo.knml.systemsolvingmethods

import com.github.varenytsiamykhailo.knml.util.Matrix
import com.github.varenytsiamykhailo.knml.util.Vector
import com.github.varenytsiamykhailo.knml.util.results.VectorResultWithStatus
import com.github.varenytsiamykhailo.knml.util.matrixMultiplicateVector
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import kotlin.math.abs

internal class JacobiMethodTest {

    @Test
    fun test1SolveSystemByJacobiMethod() {
        val A: Array<Array<Double>> =  arrayOf(
            arrayOf(115.0, -20.0, -75.0),
            arrayOf(15.0, -50.0, -5.0),
            arrayOf(6.0, 2.0, 20.0)
        )
        val B: Array<Double> = arrayOf(20.0, -40.0, 28.0)

        val result: VectorResultWithStatus = JacobiMethod().solveSystemByJacobiMethod(
            A,
            B,
            eps = 0.01,
            formSolution = true
        )

        assert(result.arrayResult != null)
        assert(result.arrayResult!!.size == 3)
        assert(result.arrayResult!![0].toString().startsWith("1.000197"))
        assert(result.arrayResult!![1].toString().startsWith("1.000833"))
        assert(result.arrayResult!![2].toString().startsWith("0.999166"))
        assert(result.vectorResult != null)
        assert(result.vectorResult!!.getN() == 3)
        assert(result.vectorResult!!.getElem(0).toString().startsWith("1.000197"))
        assert(result.vectorResult!!.getElem(1).toString().startsWith("1.000833"))
        assert(result.vectorResult!!.getElem(2).toString().startsWith("0.999166"))
        assert(result.isSuccessful)
        assert(result.errorException == null)
        assert(result.solutionObject != null)
        assert(result.solutionObject!!.solutionString.length >= 10)

        val result2: Vector = matrixMultiplicateVector(Matrix(A), result.vectorResult!!)
        for (i in result2.getElems().indices) {
            assert(abs(B[i] - result2.getElems()[i]) <= 0.5)
        }
    }

    // Test that the method does not modify the input data
    @Test
    fun test2SolveSystemByJacobiMethod() {
        val A: Matrix = Matrix(
            arrayOf(
                arrayOf(115.0, -20.0, -75.0),
                arrayOf(15.0, -50.0, -5.0),
                arrayOf(6.0, 2.0, 20.0)
            )
        )
        val B: Vector = Vector(arrayOf(20.0, -40.0, 28.0))

        val result: VectorResultWithStatus = JacobiMethod().solveSystemByJacobiMethod(
            A.getElems(),
            B.getElems()
        )
        assert(result.arrayResult != null)
        assert(result.arrayResult!!.size == 3)
        assert(result.arrayResult!![0].toString() == "1.0")
        assert(result.arrayResult!![1] == 1.0)
        assert(result.arrayResult!![2] == 1.0)
        assert(result.vectorResult != null)
        assert(result.vectorResult!!.getN() == 3)
        assert(result.vectorResult!!.getElem(0) == 1.0)
        assert(result.vectorResult!!.getElem(1) == 1.0)
        assert(result.vectorResult!!.getElem(2) == 1.0)
        assert(result.isSuccessful)
        assert(result.errorException == null)
        assert(result.solutionObject == null)

        assertArrayEquals(
            A.getElems(),
            arrayOf(
                arrayOf(115.0, -20.0, -75.0),
                arrayOf(15.0, -50.0, -5.0),
                arrayOf(6.0, 2.0, 20.0)
            )
        )
        assertArrayEquals(
            B.getElems(),
            arrayOf(20.0, -40.0, 28.0)
        )
    }

    @Test
    fun test3SolveSystemByJacobiMethod() {
        val A: Matrix = Matrix(
            arrayOf(
                arrayOf(20.9, 1.2, 2.1, 0.9),
                arrayOf(1.2, 21.2, 1.5, 2.5),
                arrayOf(2.1, 1.5, 19.8, 1.3),
                arrayOf(0.9, 2.5, 1.3, 32.1)
            )
        )
        val B: Vector = Vector(arrayOf(21.70, 27.46, 28.76, 49.72))

        val result: VectorResultWithStatus = JacobiMethod().solveSystemByJacobiMethod(
            A,
            B,
            initialApproximation = Vector(Array<Double>(4) { 1.0 })
        )
        assert(result.arrayResult != null)
        assert(result.arrayResult!!.size == 4)
        assert(result.arrayResult!![0].toString() == "0.8")
        assert(result.arrayResult!![1].toString().startsWith("1.00000"))
        assert(result.arrayResult!![2] == 1.2)
        assert(result.arrayResult!![3] == 1.4)
        assert(result.vectorResult != null)
        assert(result.vectorResult!!.getN() == 4)
        assert(result.vectorResult!!.getElem(0).toString() == "0.8")
        assert(result.vectorResult!!.getElem(1).toString().startsWith("1.00000"))
        assert(result.vectorResult!!.getElem(2) == 1.2)
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
    fun test4SolveSystemByJacobiMethod() {
        val A: Matrix = Matrix(
            arrayOf(
                arrayOf(20.9, 1.2, 2.1, 0.9),
                arrayOf(1.2, 21.2, 1.5, 2.5),
                arrayOf(2.1, 1.5, 19.8, 1.3)
            )
        )
        val B: Vector = Vector(arrayOf(21.70, 27.46, 28.76, 49.72))

        val result: VectorResultWithStatus = JacobiMethod().solveSystemByJacobiMethod(
            A,
            B,
            initialApproximation = Vector(Array<Double>(4) { 1.0 }),
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
    fun test5SolveSystemByJacobiMethod() {
        val A: Matrix = Matrix(
            arrayOf(
                arrayOf(20.9, 1.2, 2.1, 0.9),
                arrayOf(1.2, 21.2, 1.5, 2.5),
                arrayOf(2.1, 1.5, 19.8, 1.3),
                arrayOf(0.9, 2.5, 1.3, 32.1)
            )
        )
        val B: Vector = Vector(arrayOf(21.70, 27.46, 28.76))

        val result: VectorResultWithStatus = JacobiMethod().solveSystemByJacobiMethod(
            A,
            B,
            initialApproximation = Vector(Array<Double>(4) { 1.0 }),
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
    fun test6SolveSystemByJacobiMethod() {
        val A: Matrix = Matrix(
            arrayOf(
                arrayOf(20.9, 1.2, 2.1, 0.9),
                arrayOf(1.2, 21.2, 1.5, 2.5),
                arrayOf(2.1, 1.5, 19.8, 1.3),
                arrayOf(0.9, 2.5, 1.3, 32.1)
            )
        )
        val B: Vector = Vector(arrayOf(21.70, 27.46, 28.76, 49.72, 113.12399999999))

        val result: VectorResultWithStatus = JacobiMethod().solveSystemByJacobiMethod(
            A,
            B,
            initialApproximation = Vector(Array<Double>(4) { 1.0 }),
            formSolution = true
        )
        assert(result.arrayResult == null)
        assert(result.vectorResult == null)
        assert(!result.isSuccessful)
        assert(result.errorException != null)
        assert(result.solutionObject == null)
    }

    /**
     * Exception test: the size of 'inputB' vector does not match the size of 'initialApproximation' vector
     */
    @Test
    fun test7SolveSystemByJacobiMethod() {
        val A: Matrix = Matrix(
            arrayOf(
                arrayOf(20.9, 1.2, 2.1, 0.9),
                arrayOf(1.2, 21.2, 1.5, 2.5),
                arrayOf(2.1, 1.5, 19.8, 1.3),
                arrayOf(0.9, 2.5, 1.3, 32.1)
            )
        )
        val B: Vector = Vector(arrayOf(21.70, 27.46, 28.76, 49.72))

        val result: VectorResultWithStatus = JacobiMethod().solveSystemByJacobiMethod(
            A,
            B,
            initialApproximation = Vector(Array<Double>(2) { 1.0 }), // !!!
            formSolution = true
        )
        assert(result.arrayResult == null)
        assert(result.vectorResult == null)
        assert(!result.isSuccessful)
        assert(result.errorException != null)
        assert(result.solutionObject == null)
    }

    /**
     * Exception test: the sufficient condition for the convergence of the Jacobi method is not satisfied:
     * there is no diagonal dominance of the matrix inputA.
     */
    @Test
    fun test8SolveSystemByJacobiMethod() {
        val A: Matrix = Matrix(
            arrayOf(
                arrayOf(20.9, 1.2, 2.1, 0.9),
                arrayOf(1.2, 21.2, 1.5, 2.5),
                arrayOf(2.1, 1.5, 0.00000000000000000, 1.3),
                arrayOf(0.9, 2.5, 1.3, 32.1)
            )
        )
        val B: Vector = Vector(arrayOf(21.70, 27.46, 28.76, 49.72))

        val result: VectorResultWithStatus = JacobiMethod().solveSystemByJacobiMethod(
            A,
            B,
            initialApproximation = Vector(Array<Double>(4) { 1.0 }), // !!!
            formSolution = true
        )
        assert(result.arrayResult == null)
        assert(result.vectorResult == null)
        assert(!result.isSuccessful)
        assert(result.errorException != null)
        assert(result.solutionObject == null)
    }

}