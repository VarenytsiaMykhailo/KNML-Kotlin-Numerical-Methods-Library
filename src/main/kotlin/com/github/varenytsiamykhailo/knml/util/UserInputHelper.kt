package com.github.varenytsiamykhailo.knml.util

import com.github.varenytsiamykhailo.knml.systemsolvingmethods.GaussMethod
import com.github.varenytsiamykhailo.knml.util.results.VectorResultWithStatus

fun main() {
    //UserInputHelper().userInputForGaussMethod()
    //println(getMatrixWithRandomElementsAndDiagonalDominance(5, 2, 10, 100))


    val A: Matrix = Matrix(
        arrayOf(
            arrayOf(2.0, -1.0, 0.0),
            arrayOf(5.0, 4.0, 2.0),
            arrayOf(0.0, 1.0, -3.0)
        )
    )
    val B: Vector = Vector(arrayOf(3.0, 6.0, 2.0))

    val result: VectorResultWithStatus = GaussMethod().solveSystemByGaussMethodWithPivoting(
        A,
        B,
        false,
        GaussMethod.PivotingStrategy.PartialByColumn
    )
    println("res: " + result)
}

class UserInputHelper {
    fun userInputForGaussMethod() {
        print("Enter matrix size: ")
        val n = readLine()!!.toInt()
        val matrix: Matrix = Matrix(n, n)
        val vector: Vector = Vector(n)
        for (i in 0 until n) {
            print("Enter " + (i + 1) + " vertor of matrix: ")
            val str = readLine()
            val array = str!!.split(" ")
            for (j in 0 until n) {
                matrix.setElem(i, j, array[j].toDouble())
            }
        }
        println("Enter vector: ")
        val str = readLine()
        val array = str!!.split(" ")
        for (i in 0 until n) {
            vector.setElem(i, array[i].toDouble())
        }

        println(GaussMethod().solveSystemByGaussClassicMethod(matrix, vector))
    }
}