package com.github.varenytsiamykhailo.knml.systemsolvingmethods

import com.github.varenytsiamykhailo.knml.util.getMatrixWithRandomElementsAndDiagonalDominance
import com.github.varenytsiamykhailo.knml.util.getVectorWithRandomElements
import com.github.varenytsiamykhailo.knml.util.results.VectorResultWithStatus
import org.junit.jupiter.api.Test


internal class JacobiAndSeidelMethodsConvergenceTest {

    @Test
    fun test1JacobiAndSeidelMethodsConvergence() {
        val A: Array<Array<Double>> = arrayOf(
            arrayOf(115.0, -20.0, -75.0),
            arrayOf(15.0, -50.0, -5.0),
            arrayOf(6.0, 2.0, 20.0)
        )
        val B: Array<Double> = arrayOf(20.0, -40.0, 28.0)

        val resultByJacobiMethod: VectorResultWithStatus = JacobiMethod().solveSystemByJacobiMethod(
            A,
            B,
            eps = 1e-16,
            formSolution = true
        )

        val resultBySeidelMethod: VectorResultWithStatus = SeidelMethod().solveSystemBySeidelMethod(
            A,
            B,
            eps = 1e-16,
            formSolution = true
        )

        val jacobiIterations = resultByJacobiMethod.solutionObject?.iterations
        val seidelIterations = resultBySeidelMethod.solutionObject?.iterations

        println("Test1: N=3")
        println("Iterations count by Jacobi's method: " + jacobiIterations)
        println("Iterations count by Seidel's method: " + seidelIterations)
        println()

        assert(jacobiIterations!! > seidelIterations!!)
    }

    @Test
    fun test2JacobiAndSeidelMethodsConvergence() {
        val A: Array<Array<Double>> = getMatrixWithRandomElementsAndDiagonalDominance(20, 1, 10, 200).getElems()
        val B: Array<Double> = getVectorWithRandomElements(20, 1, 10).getElems()

        val resultByJacobiMethod: VectorResultWithStatus = JacobiMethod().solveSystemByJacobiMethod(
            A,
            B,
            eps = 1e-16,
            formSolution = true
        )

        val resultBySeidelMethod: VectorResultWithStatus = SeidelMethod().solveSystemBySeidelMethod(
            A,
            B,
            eps = 1e-16,
            formSolution = true
        )

        val jacobiIterations = resultByJacobiMethod.solutionObject?.iterations
        val seidelIterations = resultBySeidelMethod.solutionObject?.iterations

        println("Test2: N=20, duagonalCoefficient=200")
        println("Iterations count by Jacobi's method: " + jacobiIterations)
        println("Iterations count by Seidel's method: " + seidelIterations)
        println()

        assert(jacobiIterations!! > seidelIterations!!)
    }

    @Test
    fun test3JacobiAndSeidelMethodsConvergence() {
        val A: Array<Array<Double>> = getMatrixWithRandomElementsAndDiagonalDominance(10, 1, 10, 100).getElems()
        val B: Array<Double> = getVectorWithRandomElements(10, 1, 10).getElems()

        val resultByJacobiMethod: VectorResultWithStatus = JacobiMethod().solveSystemByJacobiMethod(
            A,
            B,
            eps = 1e-16,
            formSolution = true
        )

        val resultBySeidelMethod: VectorResultWithStatus = SeidelMethod().solveSystemBySeidelMethod(
            A,
            B,
            eps = 1e-16,
            formSolution = true
        )

        val jacobiIterations = resultByJacobiMethod.solutionObject?.iterations
        val seidelIterations = resultBySeidelMethod.solutionObject?.iterations

        println("Test3: N=10, duagonalCoefficient=100")
        println("Iterations count by Jacobi's method: " + jacobiIterations)
        println("Iterations count by Seidel's method: " + seidelIterations)
        println()

        assert(jacobiIterations!! > seidelIterations!!)
    }

    @Test
    fun test4JacobiAndSeidelMethodsConvergence() {
        val A: Array<Array<Double>> = getMatrixWithRandomElementsAndDiagonalDominance(30, 1, 10, 300).getElems()
        val B: Array<Double> = getVectorWithRandomElements(30, 1, 10).getElems()

        val resultByJacobiMethod: VectorResultWithStatus = JacobiMethod().solveSystemByJacobiMethod(
            A,
            B,
            eps = 1e-16,
            formSolution = true
        )

        val resultBySeidelMethod: VectorResultWithStatus = SeidelMethod().solveSystemBySeidelMethod(
            A,
            B,
            eps = 1e-16,
            formSolution = true
        )

        val jacobiIterations = resultByJacobiMethod.solutionObject?.iterations
        val seidelIterations = resultBySeidelMethod.solutionObject?.iterations

        println("Test4: N=30, duagonalCoefficient=300")
        println("Iterations count by Jacobi's method: " + jacobiIterations)
        println("Iterations count by Seidel's method: " + seidelIterations)
        println()

        assert(jacobiIterations!! > seidelIterations!!)
    }

    @Test
    fun test5JacobiAndSeidelMethodsConvergence() {
        val A: Array<Array<Double>> = getMatrixWithRandomElementsAndDiagonalDominance(5, 1, 10, 100).getElems()
        val B: Array<Double> = getVectorWithRandomElements(5, 1, 10).getElems()

        val resultByJacobiMethod: VectorResultWithStatus = JacobiMethod().solveSystemByJacobiMethod(
            A,
            B,
            eps = 1e-16,
            formSolution = true
        )

        val resultBySeidelMethod: VectorResultWithStatus = SeidelMethod().solveSystemBySeidelMethod(
            A,
            B,
            eps = 1e-16,
            formSolution = true
        )

        val jacobiIterations = resultByJacobiMethod.solutionObject?.iterations
        val seidelIterations = resultBySeidelMethod.solutionObject?.iterations

        println("Test2: N=5, duagonalCoefficient=100")
        println("Iterations count by Jacobi's method: " + jacobiIterations)
        println("Iterations count by Seidel's method: " + seidelIterations)
        println()

        assert(jacobiIterations!! > seidelIterations!!)
    }
}