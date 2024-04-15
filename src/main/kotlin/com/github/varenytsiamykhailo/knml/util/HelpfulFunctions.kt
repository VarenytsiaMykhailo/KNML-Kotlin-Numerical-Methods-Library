package com.github.varenytsiamykhailo.knml.util

import com.github.varenytsiamykhailo.knml.bignumbers.BigNumber
import kotlin.random.Random

fun getPretty1DDoubleArrayString(array: Array<Double>): String {
    return "elems=[" + array.joinToString(" \t") + "]"
}

fun getPretty2DDoubleArrayString(array: Array<Array<Double>>): String {
    var elemsStr: String = "\n"
    for (i in array.indices) {
        elemsStr += "\t"
        for (elem in array[i]) {
            elemsStr += "${elem} \t"
        }
        elemsStr += "\n"
    }
    return "elems=[" + elemsStr + "]"
}

fun getMachineEps(): Double {
    var eps = 1.0
    while (1 + eps > 1) {
        eps /= 2.0
    }
    return eps
}

fun getMatrixWithRandomElementsAndDiagonalDominance(n: Int, a: Int, b: Int, diagonalCoefficient: Int) : Matrix {
    val max = maxOf(-a, b)
    val min = minOf(-a, b)
    val matrix = Matrix(n, n)
    for (i in 0 until n) {
        for (j in 0 until n) {
            val element = (Random.nextInt(max - min) + min).toDouble()
            matrix.setElem(
                i,
                j,
                if (i == j) {
                    if (element != 0.0) {
                        element * diagonalCoefficient
                    } else {
                        diagonalCoefficient.toDouble()
                    }
                } else element
            )
        }
    }
    return matrix
}

fun getVectorWithRandomElements(n: Int, a: Int, b: Int) : Vector {
    val max = maxOf(-a, b)
    val min = minOf(-a, b)
    val vector = Vector(n)
    for (i in 0 until n) {
        vector.setElem(i, (Random.nextInt(max - min) + min).toDouble())
    }
    return vector
}

fun getBigNumber(n: Long): BigNumber {
    var number = ""
    for (i in  0 until n) {
        number += Random.nextInt(0, 10)
    }

    return BigNumber(number)
}

fun eyeMatrix(n: Int): Matrix {
    val matrix = Matrix(n, n)
    for (i in 0 until n) {
        for (j in 0 until n) {
            matrix.setElem(i, j, if (i == j) 1.0 else 0.0)
        }
    }
    return matrix
}

fun getSymmetricPositiveMatrixWithRandomElements(n: Int, a: Int, b: Int): Matrix {
    val max = maxOf(-a, b)
    val min = minOf(-a, b)
    val matrix = Matrix(n, n)
    for (i in 0 until n) {
        for (j in 0 until n) {
            if (i == j) {
                val element = (Random.nextInt(max - min) + min).toDouble()
                matrix.setElem(i, j, element)
            } else if (i < j) {
                val element = (Random.nextInt(max - min) + min).toDouble()
                matrix.setElem(i, j, element)
                matrix.setElem(j, i, element)
            }
        }
    }
    return matrix
}