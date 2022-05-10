package com.github.varenytsiamykhailo.knml.util.results

import com.github.varenytsiamykhailo.knml.util.Solution
import java.lang.Exception

data class DoubleResultWithStatus internal constructor(
    val doubleResult: Double? = null,
    val isSuccessful: Boolean = true,
    val errorException: Exception? = null,
    val solutionObject: Solution? = null
){
}