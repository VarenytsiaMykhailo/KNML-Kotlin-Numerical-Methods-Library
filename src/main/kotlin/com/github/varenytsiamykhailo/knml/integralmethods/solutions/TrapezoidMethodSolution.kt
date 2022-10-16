package com.github.varenytsiamykhailo.knml.integralmethods.solutions

import com.github.varenytsiamykhailo.knml.util.Solution

class TrapezoidMethodSolution internal constructor() : Solution {

    override var solutionString: String = ""
    override var iterations: Int = 0

    val trapezoidMethodRichardsonLoopIterations: MutableList<TrapezoidMethodRichardsonLoopIterationValues> = mutableListOf()

}

data class TrapezoidMethodRichardsonLoopIterationValues internal constructor(
    val trapezoidMethodResult: Double,
    val R: Double,
    val numOfSplits: Int,
    val loopIterationCount: Int
) {

}