package com.github.varenytsiamykhailo.knml.util

/**
 * Matrix implementation.
 *
 * This class implements 'matrix' (table) of the size of [n] x [m] with it's elements.
 *
 * @property [n] is the count of rows (first dimension) of the matrix, starting from 1.
 * @property [m] is the count of columns (second dimension) of the matrix, starting from 1.
 * @constructor This constructor sets default [Double] type zero values *0.0* to each element of the matrix.
 *
 * [n] is the count of rows (first dimension) of the matrix, starting from 1.
 * [m] is the count of columns (second dimension) of the matrix, starting from 1.
 *
 * Use [setElem] or [setElems] methods to set values after creating instance by this constructor.
 *
 * @see Vector
 */
class Matrix constructor(private var n: Int, private var m: Int) { // getter methods for the 'n', 'm' fields defined below

    private var elems: Array<Array<Double>> = emptyArray() // getter and setter methods for the 'elems' field defined below

    init {
        this.elems = Array(n) { Array(m) { 0.0 } }
    }

    /**
     *
     * This constructor initializes the matrix by the input [Array]<[Array]<[Double]>> elems.
     *
     * Use [setElem] or [setElems] methods to set values after creating instance by this constructor.
     */
    constructor(elems: Array<Array<Double>>) : this(elems.size, elems[0].size) {
        this.elems = elems
    }

    /**
     * Returns elem (coordinate).
     *
     * If you need all elems (coordinates) of the matrix, you can use [getElems] method.
     *
     * @param [n] is the count of rows (first dimension) of the matrix, starting from 1.
     * @param [m] is the count of columns (second dimension) of the matrix, starting from 1.
     *
     * @return This method returns elem (coordinate) of [Double] type value by [n] and [m] - position of the elem in the matrix.
     */
    fun getElem(n: Int, m: Int): Double = elems[n][m]

    /**
     * Sets elem (coordinate).
     *
     * If you need to set all elems (coordinates) of the matrix, you can use [setElems] method.
     *
     * This method sets elem (coordinate) of [Double] type value by [n] and [m] - position of the elem in the matrix.
     * @param [n] is the count of rows (first dimension) of the matrix, starting from 1.
     * @param [m] is the count of columns (second dimension) of the matrix, starting from 1.
     */
    fun setElem(n: Int, m: Int, elem: Double) {
        elems[n][m] = elem
    }

    /**
     * Returns [Array]<[Array]<[Double]>> elems (coordinates) of the matrix.
     *
     * If you need only one elem (coordinate) by it's position in the matrix, you can use [getElem] method.
     *
     * @return This method returns elems (coordinates) of [Array]<[Array]<[Double]>> type value of the matrix.
     */
    fun getElems(): Array<Array<Double>> = this.elems

    /**
     * Sets [Array]<[Array]<[Double]>> elems (coordinates) of the matrix.
     *
     * If you need to set only one elem (coordinate) by it's position in the matrix, you can use [setElem] method.
     *
     * @param [elems] This method sets elems (coordinates) of [Array]<[Array]<[Double]>> type value of the matrix.
     */
    fun setElems(elems: Array<Array<Double>>) {
        this.elems = elems
        this.n = elems.size
        this.m = elems[0].size
    }

    /**
     * Returns [n] - the count of rows (first dimension) of the matrix, starting from 1.
     *
     * @return This method returns [n] of [Int] type value.
     *
     * [n] is the count of rows (first dimension) of the matrix, starting from 1.
     */
    fun getN() = this.n

    /**
     * Returns [m] - the count of columns (second dimension) of the matrix, starting from 1.
     *
     * @return This method returns [m] of [Int] type value.
     *
     * [m] is the count of columns (second dimension) of the matrix, starting from 1.
     */
    fun getM() = this.m

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Matrix

        if (n != other.n) return false
        if (m != other.m) return false
        if (!elems.contentDeepEquals(other.elems)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = n
        result = 31 * result + m
        result = 31 * result + elems.contentDeepHashCode()
        return result
    }

    override fun toString(): String {
        var elemsStr: String = "\n"
        for (i in elems.indices) {
            elemsStr += "\t"
            for (elem in elems[i]) {
                elemsStr += "$elem "
            }
            elemsStr += "\n"
        }
        return "Matrix{" +
                "elems=[" + elemsStr +
                "], n=" + n +
                ", m=" + m +
                '}'
    }
}