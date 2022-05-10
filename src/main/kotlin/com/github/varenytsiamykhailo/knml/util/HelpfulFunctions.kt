package com.github.varenytsiamykhailo.knml.util

fun getPretty1DDoubleArrayString(array: Array<Double>): String {
    return "elems=[" + array.joinToString(" \t") + "]"
}

fun getPretty2DDoubleArrayString(array: Array<Array<Double>>): String {
    var elemsStr: String = "\n"
    for (i in array.indices) {
        elemsStr += "\t"
        for (elem in array[i]) {
            elemsStr += "${elem} \t"
        }
        elemsStr += "\n"
    }
    return "elems=[" + elemsStr + "]"
}

fun getMachineEps(): Double {
    var eps = 1.0
    while (1 + eps > 1) {
        eps /= 2.0
    }
    return eps
}