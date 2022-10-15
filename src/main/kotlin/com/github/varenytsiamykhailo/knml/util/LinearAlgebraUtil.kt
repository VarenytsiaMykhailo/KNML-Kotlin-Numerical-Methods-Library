package com.github.varenytsiamykhailo.knml.util

import kotlin.math.abs

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

/**
 * Invertible Matrix.
 *
 * Inverse of a matrix exists only if the matrix is non-singular i.e., determinant should not be 0. Using determinant and adjoint, we can easily find the inverse of a square matrix.
 *
 * This method implements inverse of matrix of the [Matrix] type.
 *
 * @param [matrix] input matrix.
 *
 * @return the result of the inversion of matrix which is represented as new [Matrix] output type.
 */
fun invertibleMatrix(matrix: Matrix): Matrix? {
    val n = matrix.getN()
    val det = matrixDeterminant(matrix, n)
    if (det == 0.0) {
        println("Singular matrix, can't find its inverse")
        return null
    }

    val inverseMatrix = Matrix(n, n)
    val adjMatrix = adjointMatrix(matrix)

    for (i in 0 until n) {
        for (j in 0 until n) {
            inverseMatrix.setElem(i, j, adjMatrix.getElem(i, j) / det)
        }
    }
    return inverseMatrix
}

/**
 * Adjoint of a Matrix.
 *
 * Adjoint (or Adjugate) of a matrix is the matrix obtained by taking the transpose of the cofactor matrix of a given square matrix is called its Adjoint or Adjugate matrix. The Adjoint of any square matrix ‘A’ (say) is represented as Adj(A).
 *
 * Product of a square matrix A with its adjoint yields a diagonal matrix, where each diagonal entry is equal to determinant of A.
 *
 * A non-zero square matrix ‘A’ of order n is said to be invertible if there exists a unique square matrix ‘B’ of order n such that,
 * adj(AB) = (adj B).(adj A)
 * adj( k A) = kn-1 adj(A)
 * A-1 = (adj A) / |A|
 * (A-1)-1 = A
 * (AB)-1 = B-1A-1
 *
 * This method implements adjoint matrix of the [Matrix] type.
 *
 * @param [matrix] input matrix.
 *
 * @return the result of adjoint of matrix which is represented as new [Matrix] output type.
 */
fun adjointMatrix(matrix: Matrix): Matrix {
    val n = matrix.getN()
    val adj = Matrix(n, n)
    if (n == 1) {
        adj.setElem(0, 0, 1.0)
        return adj
    }

    var sign: Int
    val temp = Matrix(n, n)
    for (i in 0 until n) {
        for (j in 0 until n) {
            getCofactor(matrix, temp, i, j, n)
            sign = if ((i + j) % 2 == 0) 1 else -1
            adj.setElem(j, i, sign * matrixDeterminant(temp, n - 1))
        }
    }
    return adj
}

/**
 * Matrix determinant.
 *
 * This method calculates matrix determimant of the [Double] type.
 *
 * @param [matrix] input matrix.
 * @param [n] input matrix size.
 *
 * @return the result of the calculation of determinant of given matrix which is represented as [Double] output type.
 */
fun matrixDeterminant(matrix: Matrix, n: Int): Double {
    if (n == 1) {
        return matrix.getElem(0, 0)
    }

    var det = 0.0
    val temp = Matrix(n, n)
    var sign = 1

    for (f in 0 until n) {
        getCofactor(matrix, temp, 0, f, n)
        det += sign * matrix.getElem(0, f) * matrixDeterminant(temp, n - 1)
        sign = -sign
    }

    return det
}

private fun getCofactor(a: Matrix, temp: Matrix, p: Int, q: Int, n: Int) {
    var i = 0
    var j = 0

    for (row in 0 until n) {
        for (col in 0 until n) {
            if (row != p && col != q) {
                temp.setElem(i, j++, a.getElem(row, col))
                if (j == n - 1) {
                    j = 0
                    i++
                }
            }
        }
    }
}

/**
 * Matrix norm.
 *
 * This method calculates matrix norm of the [Double] type.
 *
 * @param [matrix] input matrix.
 *
 * @return the result of the calculation of norm of given matrix which is represented as [Double] output type.
 */
fun getMatrixNorm(matrix: Matrix): Double {
    var max = -1.0
    val sumVec = Vector(matrix.getN())
    for (i in 0 until matrix.getN()) {
        var sum = 0.0
        for (j in 0 until matrix.getN()) {
            sum += abs(matrix.getElem(i, j))
        }
        sumVec.setElem(i, sum)
    }
    for (x in sumVec.getElems()) {
        if (max <= x) {
            max = x
        }
    }
    return max
}

/**
 * Vector norm.
 *
 * This method calculates vector norm of the [Double] type.
 *
 * @param [vector] input vector.
 *
 * @return the result of the calculation of vector of given matrix which is represented as [Double] output type.
 */
fun getNormaOfVector(vector: Vector): Double {
    var max = -1.0
    for (x in vector.getElems()) {
        if (max <= abs(x)) {
            max = x
        }
    }
    return max
}
