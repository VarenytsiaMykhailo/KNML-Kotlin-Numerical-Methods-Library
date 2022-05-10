package com.github.varenytsiamykhailo.knml.util.results

import com.github.varenytsiamykhailo.knml.util.Solution
import com.github.varenytsiamykhailo.knml.util.Vector
import java.lang.Exception

data class VectorResultWithStatus internal constructor(
    val vectorResult: Vector? = null,
    val arrayResult: Array<Double>? = null,
    val isSuccessful: Boolean = true,
    val errorException: Exception? = null,
    val solutionObject: Solution? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VectorResultWithStatus

        if (vectorResult != other.vectorResult) return false
        if (arrayResult != null) {
            if (other.arrayResult == null) return false
            if (!arrayResult.contentEquals(other.arrayResult)) return false
        } else if (other.arrayResult != null) return false
        if (isSuccessful != other.isSuccessful) return false
        if (errorException != other.errorException) return false
        if (solutionObject != other.solutionObject) return false

        return true
    }

    override fun hashCode(): Int {
        var result = vectorResult?.hashCode() ?: 0
        result = 31 * result + (arrayResult?.contentHashCode() ?: 0)
        result = 31 * result + isSuccessful.hashCode()
        result = 31 * result + (errorException?.hashCode() ?: 0)
        result = 31 * result + (solutionObject?.hashCode() ?: 0)
        return result
    }
}