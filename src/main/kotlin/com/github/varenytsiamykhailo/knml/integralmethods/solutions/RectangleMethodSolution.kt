package com.github.varenytsiamykhailo.knml.integralmethods.solutions

import com.github.varenytsiamykhailo.knml.util.Solution

class RectangleMethodSolution internal constructor() : Solution {

    override var solutionString: String = ""

    val rectangleMethodRichardsonLoopIterations: MutableList<RectangleMethodRichardsonLoopIterationValues> = mutableListOf()

}

data class RectangleMethodRichardsonLoopIterationValues internal constructor(
    val rectangleMethodResult: Double,
    val R: Double,
    val numOfSplits: Int,
    val loopIterationCount: Int
) {

}