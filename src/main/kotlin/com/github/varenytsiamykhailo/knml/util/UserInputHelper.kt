package com.github.varenytsiamykhailo.knml.util

import com.github.varenytsiamykhailo.knml.systemsolvingmethods.GaussMethod

fun main() {
    UserInputHelper().userInputForGaussMethod()
    println(getMatrixWithRandomElementsAndDiagonalDominance(5, 2, 10, 100))
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
            str!!.split(" ")
            for (j in 0 until n) {
                matrix.setElem(i, j, str[j].toDouble())
            }
        }
        println("Enter vector: ")
        val str = readLine()
        str!!.split(" ")
        for (i in 0 until n) {
            vector.setElem(i, str[i].toDouble())
        }

        println(GaussMethod().solveSystemByGaussClassicMethod(matrix, vector))
    }
}