package com.github.varenytsiamykhailo.knml.util

class Interval(var a: Double, var b: Double) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Interval

        if (a != other.a) return false
        if (b != other.b) return false

        return true
    }

    override fun hashCode(): Int {
        var result = a.hashCode()
        result = 31 * result + b.hashCode()
        return result
    }

    override fun toString(): String {
        return "Interval[" + a + "; " + b + ']'
    }
}