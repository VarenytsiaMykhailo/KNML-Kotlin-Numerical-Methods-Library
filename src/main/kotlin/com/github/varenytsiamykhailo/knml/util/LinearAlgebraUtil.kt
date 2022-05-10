package com.github.varenytsiamykhailo.knml.util

/**
 * Scalar product of two vectors.
 *
 * This method implements scalar product of two input vectors of the [Vector] type.
 *
 * @param [vector1] the first input vector.
 * @param [vector2] the second input vector.
 *
 * @return the result of the scalar product of two vectors which is represented as [Double] value.
 */
@Throws(java.lang.Exception::class)
fun scalarProduct(vector1: Vector, vector2: Vector): Double {
    require(vector1.getN() == vector2.getN()) { "The size of 'v1' does not equals the size of 'v2'. The size of 'v1.n' must be equal to the size of 'v2.n'." }

    var result = 0.0

    for (i in 0 until vector1.getN()) {
        result += vector1.getElem(i) * vector2.getElem(i)
    }

    return result
}

/**
 * Matrix multiplicate vector.
 *
 * This method implements multiplication of input matrix of the [Matrix] type and vector of the [Vector] type.
 *
 * @param [matrix] the input matrix
 * @param [vector] the input vector.
 *
 * @return the result of the multiplication of matrix and vector which is represented as new [Vector] output type.
 */
@Throws(Exception::class)
fun matrixMultiplicateVector(matrix: Matrix, vector: Vector): Vector {
    require(matrix.getM() == vector.getN()) { "The size of 'matrix' does not match to size of 'vector'." }

    val vectorElemsResult: Array<Double> = Array(matrix.getN()) { 0.0 }

    // index 'i' matches to 'matrix' row and result vector row
    for (i in 0 until matrix.getN()) {
        // index 'j' matches to 'matrix' column and 'vector' row
        for (j in 0 until matrix.getM()) {
            vectorElemsResult[i] += matrix.getElem(i, j) * vector.getElem(j)
        }
    }

    return Vector(vectorElemsResult)
}

/**
 * Matrix multiplicate matrix.
 *
 * This method implements matrix multiplication of two input matriсes of the [Matrix] type.
 *
 * @param [matrix1] the first input matrix.
 * @param [matrix2] the second input matrix.
 *
 * @return the result of the multiplication of two matriсes which is represented as new [Matrix] output type.
 */
@Throws(java.lang.Exception::class)
fun matrixMultiplicateMatrix(matrix1: Matrix, matrix2: Matrix): Matrix {
    require(matrix1.getM() == matrix2.getN()) { "The size of 'matrix1.m' does not match to size of 'matrix2.n'" }

    val matrixElemsResult: Array<Array<Double>> = Array(matrix1.getN()) { Array(matrix2.getM()) { 0.0 } }

    // index 'i' matches to 'matrix1' row and result matrix row
    for (i in 0 until matrix1.getN()) {
        // index 'j' matches to 'matrix2' column and result matrix column
        for (j in 0 until matrix2.getM()) {
            // index 'k' matches to 'matrix1' column and 'matrix2' row
            for (k in 0 until matrix1.getM()) {
                matrixElemsResult[i][j] += matrix1.getElem(i, k) * matrix2.getElem(k, j)
            }
        }
    }

    return Matrix(matrixElemsResult)
}

/**
 * Matrix transposition.
 *
 * This method implements matrix transposition of t matrix of the [Matrix] type.
 *
 * @param [matrix] input matrix.
 *
 * @return the result of the transposition of two matrix which is represented as new [Matrix] output type.
 */
fun transposeMatrix(matrix: Matrix): Matrix {
    val matrixElems: Array<Array<Double>> = matrix.getElems()
    val resultMatrixElems = Array(matrixElems[0].size) { Array(matrixElems.size) { 0.0 } }

    for (i in matrixElems.indices) {
        for (j in matrixElems[0].indices) {
            resultMatrixElems[j][i] = matrixElems[i][j]
        }
    }

    return Matrix(resultMatrixElems)
}