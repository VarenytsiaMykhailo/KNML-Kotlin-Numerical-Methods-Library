package com.github.varenytsiamykhailo.knml.integralmethods.solutions

import com.github.varenytsiamykhailo.knml.util.Solution

class SimpsonMethodSolution internal constructor() : Solution {

    override var solutionString: String = ""

    val simpsonMethodRichardsonLoopIterations: MutableList<SimpsonMethodRichardsonLoopIterationValues> = mutableListOf()

}

data class SimpsonMethodRichardsonLoopIterationValues internal constructor(
    val simpsonMethodResult: Double,
    val R: Double,
    val numOfSplits: Int,
    val loopIterationCount: Int
) {

}