package com.github.varenytsiamykhailo.knml.util.results

import com.github.varenytsiamykhailo.knml.util.Interval
import com.github.varenytsiamykhailo.knml.util.Solution
import java.lang.Exception

data class IntervalResultWithStatus internal constructor(
    val intervalResult: Interval? = null,
    val isSuccessful: Boolean = true,
    val errorException: Exception? = null,
    val solutionObject: Solution? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as IntervalResultWithStatus

        if (intervalResult != other.intervalResult) return false
        if (isSuccessful != other.isSuccessful) return false
        if (errorException != other.errorException) return false
        if (solutionObject != other.solutionObject) return false

        return true
    }

    override fun hashCode(): Int {
        var result = intervalResult?.hashCode() ?: 0
        result = 31 * result + isSuccessful.hashCode()
        result = 31 * result + (errorException?.hashCode() ?: 0)
        result = 31 * result + (solutionObject?.hashCode() ?: 0)
        return result
    }
}