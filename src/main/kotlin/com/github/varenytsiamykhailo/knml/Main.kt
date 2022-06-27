@file:JvmName("Main")

package com.github.varenytsiamykhailo.knml

import com.github.varenytsiamykhailo.knml.optimizationmethods.FibonacciMethod
import com.github.varenytsiamykhailo.knml.optimizationmethods.GoldenSectionMethod
import com.github.varenytsiamykhailo.knml.optimizationmethods.SvennMethod
import com.github.varenytsiamykhailo.knml.systemsolvingmethods.JacobiMethod
import com.github.varenytsiamykhailo.knml.systemsolvingmethods.SeidelMethod
import com.github.varenytsiamykhailo.knml.util.*
import com.github.varenytsiamykhailo.knml.util.results.DoubleResultWithStatus
import com.github.varenytsiamykhailo.knml.util.results.IntervalResultWithStatus
import com.github.varenytsiamykhailo.knml.util.results.VectorResultWithStatus
import java.lang.Math.abs

fun main() {

    val svennMethod: IntervalResultWithStatus =
        SvennMethod().findIntervalBySvennMin(-10.0, 0.001, true) { x -> (x - 1) * (x - 1) + abs(x + 5) * abs(x + 5) }

    println(svennMethod.isSuccessful)
    println(svennMethod.solutionObject!!.solutionString)
    println(svennMethod.intervalResult)
    println(svennMethod.errorException)

    val fibonacciMethod: DoubleResultWithStatus = FibonacciMethod().findExtremaByFibonacciMethod(
        -10.0,
        7.0,
        0.001,
        true
    ) {
            x -> (x - 1) * (x - 1) + abs(x + 5) * abs(x + 5)
    }

    println(fibonacciMethod.isSuccessful)
    println(fibonacciMethod.solutionObject!!.solutionString)
    println(fibonacciMethod.doubleResult)
    println(fibonacciMethod.errorException)

    val goldenSectionMethod: DoubleResultWithStatus = GoldenSectionMethod().findExtremaByGoldenSectionMethod(
        -10.0,
        7.0,
        0.001,
        true
    ) {
            x -> (x - 1) * (x - 1) + abs(x + 5) * abs(x + 5)
    }

    println(goldenSectionMethod.isSuccessful)
    println(goldenSectionMethod.solutionObject!!.solutionString)
    println(goldenSectionMethod.doubleResult)
    println(goldenSectionMethod.errorException)


    val m1 = Matrix(2, 3)
    val v1 = Vector(2)


//val resultVector: Vector = matrixMultiplicateVector(m1, v1)


//resultVector.getElems()


/*
val A: Array<Array<Double>> =  arrayOf(
    arrayOf(115.0, -20.0, -75.0),
    arrayOf(15.0, -50.0, -5.0),
    arrayOf(6.0, 2.0, 20.0)
)
val B: Array<Double> = arrayOf(20.0, -40.0, 28.0)

val result: VectorResultWithStatus = GaussMethod().solveSystemByGaussClassicMethod(A, B, true)
println(result.vectorResult)
println(result.solutionObject!!.solutionString)

 */


    val A: Array<Array<Double>> = arrayOf(
        arrayOf(115.0, -20.0, -75.0),
        arrayOf(15.0, -50.0, -5.0),
        arrayOf(6.0, 2.0, 20.0)
    )
    val B: Array<Double> = arrayOf(20.0, -40.0, 28.0)

    val result: VectorResultWithStatus = SeidelMethod().solveSystemBySeidelMethod(
        A,
        B,
        initialApproximation = Array(3) { 1.0 },
        eps = 0.01,
        formSolution = true
    )



    println(result.vectorResult)
    println(result.isSuccessful)
    println(result.errorException)
    println(result.solutionObject!!.solutionString)

    val A1: Matrix = Matrix(
        arrayOf(
            arrayOf(20.9, 1.2, 2.1, 0.9),
            arrayOf(1.2, 21.2, 1.5, 2.5),
            arrayOf(2.1, 1.5, 19.8, 1.3),
            arrayOf(0.9, 2.5, 1.3, 32.1)
        )
    )
    val B1: Vector = Vector(arrayOf(21.70, 27.46, 28.76, 49.72))

    val result1: VectorResultWithStatus = SeidelMethod().solveSystemBySeidelMethod(
        A1,
        B1,
        initialApproximation = Vector(Array<Double>(4) { 1.0 }),
        formSolution = true
    )
    println(result1.vectorResult)
    println(result1.isSuccessful)
    println(result1.errorException)
    println(result1.solutionObject!!.solutionString)

    val A2: Matrix = Matrix(
        arrayOf(
            arrayOf(20.9, 1.2, 2.1, 0.9),
            arrayOf(1.2, 21.2, 1.5, 2.5),
            arrayOf(2.1, 1.5, 19.8, 1.3),
            arrayOf(0.9, 2.5, 1.3, 32.1)
        )
    )
    val B2: Vector = Vector(arrayOf(21.70, 27.46, 28.76, 49.72))

    println("======= JACOBI")
    val result2: VectorResultWithStatus = JacobiMethod().solveSystemByJacobiMethod(
        A2,
        B2,
        initialApproximation = Vector(Array<Double>(4) { 1.0 })
    )
    println(result2.vectorResult)
    println(result2.isSuccessful)
    println(result2.errorException)


/*
val result1: DoubleResultWithStatus = SimpsonMethod().solveIntegralBySimpsonMethod(0.0, 1.0, 0.00000001, true) {
    exp(it)
}
println(result1.doubleResult)
println(result1.isSuccessful)
println(result1.errorException)
println(result1.solutionObject?.solutionString)


val result2: DoubleResultWithStatus = SimpsonMethod().solveIntegralBySimpsonMethod(
    0.0,
    1.0,
    0.00000001,
    integralFunction = fun(x: Double): Double = sin(x))
println(result2.doubleResult)
println(result2.isSuccessful)
println(result2.errorException)
println(result2.solutionObject)
*/

/*
val A = Matrix(
    arrayOf(
        arrayOf(4.0, 1.0, 0.0, 0.0),
        arrayOf(1.0, 4.0, 1.0, 0.0),
        arrayOf(0.0, 1.0, 4.0, 1.0),
        arrayOf(0.0, 0.0, 1.0, 4.0)
    )
)

val B = Vector(arrayOf(5.0, 6.0, 6.0, 5.0))

val res0: VectorResultWithStatus = ThomasMethod().solveSystemByThomasMethod(A, B, true)

println(res0.arrayResult.contentToString())
println(res0.vectorResult)
println(res0.isSuccessful)
println(res0.errorException)
println(res0.solutionString)
*/
/*
val res0: VectorResultWithStatus = JacobiMethod().solveSystemByJacobiMethod(
    arrayOf(
        arrayOf(115.0, -20.0, -75.0),
        arrayOf(15.0, -50.0, -5.0),
        arrayOf(6.0, 2.0, 20.0)
    ),
    arrayOf(20.0, -40.0, 28.0),
    eps = 0.01,
    formSolutionString = true
)
println(res0.arrayResult.contentToString())
println(res0.vectorResult)
println(res0.isSuccessful)
println(res0.errorException)
println(res0.solutionString)
*/


/*
    println("---------------------------")
    val A = Matrix(
        arrayOf(
            arrayOf(115.0, -20.0, -75.0),
            arrayOf(15.0, -50.0, -5.0),
            arrayOf(6.0, 2.0, 20.0)
        )
    )

    val B = Vector(arrayOf(20.0, -40.0, 28.0))
    println(A)
    println(B)

    val res2: VectorResultWithStatus  = JacobiMethod().solveSystemByJacobiMethod(
        A.getElems(),
        B.getElems()
    )
    println(res2.arrayResult.contentToString())
    println(res2.vectorResult)
    println(res2.isSuccessful)
    println(res2.errorException)
    println(res2.solutionString)
    println(A)
    println(B)
*/

/*
val A2 = Matrix(
    arrayOf(
        arrayOf(20.9, 1.2, 2.1, 0.9),
        arrayOf(1.2, 21.2, 1.5, 2.5),
        arrayOf(2.1, 1.5, 19.8, 1.3),
        arrayOf(0.9, 2.5, 1.3, 32.1)
    )
)
val B2 = Vector(arrayOf(21.70, 27.46, 28.76, 49.72))

val res2: VectorResultWithStatus = JacobiMethod().solveSystemByJacobiMethod(
    A2,
    B2,
    initialApproximation = Vector(Array<Double>(4) { 1.0 }),
    eps = 0.1,
    formSolutionString = false
)

println("array: " + res2.arrayResult.contentToString())
println("vector: " + res2.vectorResult)
println("isSuccessful: " + res2.isSuccessful)
println("errorException: " + res2.errorException)
println("solutionString: " + res2.solutionString)








println("Input data:")
println(A2)
println(B2)
println("Received solution object:")
println(res2.arrayResult.contentToString())
println(res2.vectorResult)
println(res2.isSuccessful)
println(res2.errorException)

 */
/*
    val res2_2: VectorResultWithStatus = JacobiMethod().solveSystemByJacobiMethod(
        A2.getElems(),
        B2.getElems(),
        eps = 0.001
    )
    println(res2_2.arrayResult.contentToString())
    println(res2_2.vectorResult)
    println(res2_2.isSuccessful)
    println(res2_2.errorException)
    println(res2_2.solutionString)
    println(A2)
    println(B2)

    val A3 = Matrix(
        arrayOf(
            arrayOf(115.0, -20.0, -75.0),
            arrayOf(15.0, -50.0, -5.0)
        )
    )

    val B3 = Vector(arrayOf(20.0, -40.0, 28.0))

    val res3: VectorResultWithStatus = JacobiMethod().solveSystemByJacobiMethod(
        A3.getElems(),
        B3.getElems(),
        eps = 0.001
    )
    println(A3)
    println(B3)
    println(res3.arrayResult.contentToString())
    println(res3.vectorResult)
    println(res3.isSuccessful)
    println(res3.errorException)
    println(res3.solutionString)


    val A4: Matrix = Matrix(
        arrayOf(
            arrayOf(20.9, 1.2, 2.1, 0.9),
            arrayOf(1.2, 21.2, 1.5, 2.5),
            arrayOf(2.1, 1.5, 19.8, 1.3),
            arrayOf(2.1, 1.5, 19.8, 1.3)
        )
    )
    val B4: Vector = Vector(arrayOf(21.70, 27.46, 28.76,))

    val res4: VectorResultWithStatus = JacobiMethod().solveSystemByJacobiMethod(
        A4,
        B4,
        eps = 0.001,
        formSolutionString = true
    )
    println(res4.solutionString)
    println(res4.errorException)

 */
/*
val A1: Array<Array<Double>> = arrayOf(
    arrayOf(4.0, 1.0, 0.0, 0.0),
    arrayOf(1.0, 4.0, 1.0, 0.0),
    arrayOf(0.0, 1.0, 4.0, 1.0),
    arrayOf(0.0, 0.0, 1.0, 4.0)
)

val B1: Array<Double> = arrayOf(5.0, 6.0, 6.0, 5.0)

val A2: Array<Array<Double>> = arrayOf(
    arrayOf(2.0, -1.0, 0.0),
    arrayOf(5.0, 4.0, 2.0),
    arrayOf(0.0, 1.0, -3.0)
)

val B2 = arrayOf(3.0, 6.0, 2.0)

val thomasMethod: ThomasMethod = ThomasMethod()

val res1: Array<Double> = thomasMethod.solveSystemByThomasMethod(A1, B1)
println(res1)
val res2: Array<Double> = thomasMethod.solveSystemByThomasMethod(A2, B2)
println(res2)
*/
/*
println("helloworld")

test1()

println()
println()
println()
println()

val m1: Matrix = Matrix(2, 3)

println(m1)
println(m1.getElem(1, 2))

val m2: Matrix = Matrix(
    arrayOf(
        arrayOf(100.0, 30.0, -70.0),
        arrayOf(15.0, -50.0, -5.0),
        arrayOf(6.0, 2.0, -20.0)
    )
)
println(m2)
println(m2.getElem(1, 2))

m2.setElem(1,2, 15.0)
println(m2.getElem(1, 2))

println(m2.getElems().joinToString { it -> "\n" + it.joinToString() })
m2.setElems(arrayOf(arrayOf(15.0, 22.9)))
println(m2)
println(m2.getN())
println(m2.getM())

val v: Vector = Vector(arrayOf(13.0, 15.3, 28.9))
println(v)
*/

}

private fun test1() {
    val v1: Vector = Vector(arrayOf(1.0, 2.5, 2.0))
    val v2: Vector = Vector(arrayOf(2.0, 2.0, 2.5))
    // 2 + 5 + 5 = 12
    val result: Double = scalarProduct(v1, v2)
    println(result)

    val m1: Matrix = Matrix(
        arrayOf(
            arrayOf(1.0, 1.0, 1.0),
            arrayOf(2.0, 2.0, 2.0),
            arrayOf(3.0, 3.0, 3.0),
            arrayOf(4.0, 4.0, 4.0),
        )
    )
    val v3: Vector = Vector(arrayOf(2.0, 2.0, 2.5))
    val resultVector: Vector = matrixMultiplicateVector(m1, v3)
    println(resultVector)

    val m2: Matrix = Matrix(
        arrayOf(
            arrayOf(5.0, 8.0, -4.0),
            arrayOf(6.0, 9.0, -5.0),
            arrayOf(4.0, 7.0, -3.0)
        )
    )
    val m3: Matrix = Matrix(
        arrayOf(
            arrayOf(3.0, 2.0, 5.0),
            arrayOf(4.0, -1.0, 3.0),
            arrayOf(9.0, 6.0, 5.0)
        )
    )
    val r1: Matrix = matrixMultiplicateMatrix(m2, m3)
    println(r1)

    val m4: Matrix = Matrix(
        arrayOf(
            arrayOf(1.0, 2.0, 3.0),
            arrayOf(4.0, 5.0, 6.0)
        )
    )
    val r2: Matrix = transposeMatrix(m4)
    println(r2)

}