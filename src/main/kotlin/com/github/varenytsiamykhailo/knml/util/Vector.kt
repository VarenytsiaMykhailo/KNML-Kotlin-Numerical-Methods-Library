package com.github.varenytsiamykhailo.knml.util

/**
 * Vector implementation.
 *
 * This class implements 'linear vector' with it's elements (coordinates).
 *
 * @property [n] is the size (dimension/index) of the vector, starting from 1.
 * @constructor This constructor sets default [Double] type zero values *0.0* to each element of the vector.
 *
 * [n] is the size (dimension/index) of the vector, starting from 1.
 *
 * Use [setElem] or [setElems] methods to set values after creating instance by this constructor.
 *
 * @see Matrix
 */
class Vector constructor(private var n: Int) {

    private var elems: Array<Double> = emptyArray()

    init {
        this.elems = Array(n) { 0.0 }
    }

    /**
     * This constructor initializes the vector by the input [Array]<[Double]> elems (coordinates).
     *
     * Use [setElem] or [setElems] methods to set values after creating instance by this constructor.
     */
    constructor(elems: Array<Double>) : this(elems.size) {
        this.elems = elems
    }

    /**
     * Returns elem (coordinate).
     *
     * If you need all elems (coordinates) of the vector, you can use [getElems] method.
     *
     * @param [n] is the size (dimension/index) of the vector, starting from 1.
     *
     * @return This method returns elem (coordinate) of [Double] type value by [n] - position of the elem in the vector.
     */
    fun getElem(n: Int): Double = elems[n]

    /**
     * Set elem (coordinate).
     *
     * This method sets elem (coordinate) of [Double] type value into [n] - position of the elem in the vector.
     *
     * If you need to set all elems (coordinates) of the vector, you can use [setElems] method.
     *
     * @param [n] is the size (dimension/index) of the vector, starting from 1.
     */
    fun setElem(n: Int, elem: Double) {
        elems[n] = elem
    }

    /**
     * Returns [Array]<[Double]> elems (coordinates) of the vector.
     *
     * If you need only one elem (coordinate) by it's position in the vector, you can use [getElem] method.
     *
     * @return This method returns elems (coordinates) of [Array]<[Double]> type value of the vector.
     */
    fun getElems(): Array<Double> = this.elems

    /**
     * Sets [Array]<[Double]> elems (coordinates) into the vector.
     *
     * If you need to set only one elem (coordinate) by it's position in the vector, you can use [setElem] method.
     *
     * @param [elems] This method sets elems (coordinates) of [Array]<[Double]> type value into the vector.
     *
     */
    fun setElems(elems: Array<Double>) {
        this.elems = elems
        this.n = elems.size
    }

    /**
     * Returns [n] - the size (dimension/index) of the vector, starting from 1.
     *
     * @return This method returns [n] of [Int] type value.
     *
     * [n] is the size (dimension/index) of the vector, starting from 1.
     */
    fun getN() = this.n

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Vector

        if (n != other.n) return false
        if (!elems.contentEquals(other.elems)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = n
        result = 31 * result + elems.contentHashCode()
        return result
    }

    override fun toString(): String {
        return "Vector{" +
                "elems=[" + elems.joinToString(" ") +
                "], n=" + n +
                '}'
    }
}