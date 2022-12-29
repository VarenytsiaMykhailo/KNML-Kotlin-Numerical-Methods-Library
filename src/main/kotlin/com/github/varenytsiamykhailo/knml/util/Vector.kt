package com.github.varenytsiamykhailo.knml.util

import kotlin.math.pow
import kotlin.math.sqrt

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

    /**
     * Vector multiplicate number.
     *
     * This method implements multiplication of current vector of the [Vector] type and number of the [Double] type.
     *
     * @param [number] the input number with type Double.
     *
     * @return the result of the multiplication of vector and number which is represented as new [Vector] output type.
     *
     * Asymptotic complexity: O(n)
     */
    fun multiply(number: Double): Vector {
        val vec = Vector(this.getElems())
        vec.getElems().forEach { it * number }
        return vec
    }

    /**
     * Vector multiplicate Vector.
     *
     * This method implements multiplication of current vector of the [Vector] type and Vector of the [Vector] type.
     *
     * @param [vector] the input vector with type Vector.
     *
     * @return the result of the multiplication of two vectors which is represented as new [Vector] output type.
     *
     * Asymptotic complexity: O(n)
     */
    fun multiply(vector: Vector): Vector {
        require(this.getN() == vector.getN()) { "The size of this vector does not match to size of 'vector'." }

        val vec = Vector(this.getElems())

        for (i in 0 until vector.getN()) {
            vec.setElem(i, vec.getElem(i) * vector.getElem(i))
        }

        return vec
    }

    /**
     * Vector subtract vector.
     *
     * This method implements vector subtraction of this vector and input vector of the [Vector] type.
     *
     * @param [vector] the input vector.
     *
     * @return the result of the subtraction of two vectors which is represented as new [Vector] output type.
     *
     * Asymptotic complexity: O(n)
     */
    fun sub(vector: Vector): Vector {
        require(this.getN() == vector.getN()) { "The size of this vector does not match to size of 'vector2.n'" }
        val n = this.getN()
        val v = Vector(n)
        for (i in 0 until n) {
            v.setElem(i, this.getElem(i) - vector.getElem(i))
        }
        return v
    }

    /**
     * Scalar product of two vectors.
     *
     * This method implements scalar product of this vector and input vector of the [Vector] type.
     *
     * @param [vector2] the input vector.
     *
     * @return the result of the scalar product of two vectors which is represented as [Double] value.
     */
    @Throws(java.lang.Exception::class)
    fun scalarProduct(vector: Vector): Double {
        require(this.getN() == vector.getN()) { "The size of this vector does not equals the size of 'v2'. The size of 'v1.n' must be equal to the size of 'v2.n'." }

        var result = 0.0

        for (i in 0 until this.getN()) {
            result += this.getElem(i) * vector.getElem(i)
        }

        return result
    }

    /**
     * Vector addition vector.
     *
     * This method implements vector addition of this vector and input vector of the [Vector] type.
     *
     * @param [vector] the input vector.
     *
     * @return the result of the addition of two vectors which is represented as new [Vector] output type.
     *
     * Asymptotic complexity: O(n)
     */
    fun add(vector: Vector): Vector {
        require(this.getN() == vector.getN()) { "The size of this vector does not match to size of 'vector2.n'" }

        val n = this.getN()
        val v = Vector(n)
        for (i in 0 until n) {
            v.setElem(i, this.getElem(i) + vector.getElem(i))
        }
        return v
    }

    /**
     * Vector norm.
     *
     * This method calculates vector norm of the [Double] type.
     *
     * @return the result of the calculation of norm of current vector which is represented as [Double] output type.
     *
     * Asymptotic complexity: O(n)
     */
    fun norm(): Double {
        var sumOfSqrs = 0.0
        for (i in 0 until this.getN()) {
            sumOfSqrs += this.getElem(i).pow(2.0)
        }
        return sqrt(sumOfSqrs)
    }

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