package com.github.varenytsiamykhailo.knml.util

import org.junit.jupiter.api.Test

class SVDTest {
    @Test
    fun test() {
        val m = Matrix(
            arrayOf(
                arrayOf(0.96, 1.72),
                arrayOf(2.28, 0.96)
            )
        )

        val svd1 = SVD(m)
        val u = svd1.getU()
        val s = svd1.getS()
        val v = svd1.getV()

        println("U: " + getPretty2DDoubleArrayString(svd1.getU().getElems()))
        println("S: " + getPretty2DDoubleArrayString(svd1.getS().getElems()))
        println("V: " + getPretty2DDoubleArrayString(svd1.getV().getElems()))
        println(getPretty2DDoubleArrayString(u.multiply(s).multiply(v.transpose()).getElems()))

        val U = Matrix(
            arrayOf(
                arrayOf(0.6, 0.8),
                arrayOf(0.8, -0.6)
            )
        )
        val S = Matrix(
            arrayOf(
                arrayOf(3.0, 0.0),
                arrayOf(0.0, 1.0)
            )
        )
        val V = Matrix(
            arrayOf(
                arrayOf(0.8, -0.6),
                arrayOf(0.6, 0.8)
            )
        )
        println(getPretty2DDoubleArrayString(U.multiply(S).multiply(V.transpose()).getElems()))
    }
}