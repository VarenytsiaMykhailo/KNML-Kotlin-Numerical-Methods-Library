package com.github.varenytsiamykhailo.knml.util.results

import com.github.varenytsiamykhailo.knml.util.Matrix
import com.github.varenytsiamykhailo.knml.util.Solution
import java.lang.Exception

data class MatrixResultWithStatus internal constructor(
    val matrixResult: Matrix? = null,
    val arrayResult: Array<Array<Double>>? = null,
    val isSuccessful: Boolean = true,
    val errorException: Exception? = null,
    val solutionObject: Solution? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MatrixResultWithStatus

        if (matrixResult != other.matrixResult) return false
        if (arrayResult != null) {
            if (other.arrayResult == null) return false
            if (!arrayResult.contentDeepEquals(other.arrayResult)) return false
        } else if (other.arrayResult != null) return false
        if (isSuccessful != other.isSuccessful) return false
        if (errorException != other.errorException) return false
        if (solutionObject != other.solutionObject) return false

        return true
    }

    override fun hashCode(): Int {
        var result = matrixResult?.hashCode() ?: 0
        result = 31 * result + (arrayResult?.contentDeepHashCode() ?: 0)
        result = 31 * result + isSuccessful.hashCode()
        result = 31 * result + (errorException?.hashCode() ?: 0)
        result = 31 * result + (solutionObject?.hashCode() ?: 0)
        return result
    }
}