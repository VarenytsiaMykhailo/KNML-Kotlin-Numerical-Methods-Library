package com.github.varenytsiamykhailo.knml.util

import kotlin.math.hypot


/** QR Decomposition.
 *
 * QR decomposition is a decomposition of a matrix A into a product A = QR of an orthogonal matrix
 * Q and an upper triangular matrix R. QR decomposition is often used to solve the linear least
 * squares problem and is the basis for a particular eigenvalue algorithm, the QR algorithm.
 *
 * For an m-by-n matrix A with m >= n, the QR decomposition is an m-by-n
 * orthogonal matrix Q and an n-by-n upper triangular matrix R so that
 * A = Q*R.
 *
 * The QR decompostion always exists, even if the matrix does not have full rank, so the constructor
 * will never fail. The primary use of the QR decomposition is in the least squares solution of
 * nonsquare systems of simultaneous linear equations. This will fail if isFullRank() returns false.
 *
 *  Asymptotic complexity: O(n^3).
 */
class QRDecomposition(matrix: Matrix) {
    private val QR: Array<Array<Double>>

    /** Row and column dimensions.
     * @serial column dimension.
     * @serial row dimension.
     */
    private val m: Int
    private val n: Int

    /** Array for internal storage of diagonal of R.
     * @serial diagonal of R.
     */
    private val Rdiag: Array<Double>

    val isFullRank: Boolean
        get() {
            for (j in 0 until n) {
                if (Rdiag[j] == 0.0) return false
            }
            return true
        }

    /** Return the Householder vectors
     * @return Lower trapezoidal matrix whose columns define the reflections as [Matrix] output type.
     */
    private val H: Matrix
        get() {
            val X = Matrix(m, n)
            val H: Array<Array<Double>> = X.getElems()
            for (i in 0 until m) {
                for (j in 0 until n) {
                    if (i >= j) {
                        H[i][j] = QR[i][j]
                    } else {
                        H[i][j] = 0.0
                    }
                }
            }
            return X
        }

    /** Return the upper triangular factor as [Matrix] output type.
     * @return R
     */
    val R: Matrix
        get() {
            val X = Matrix(n, n)
            val R: Array<Array<Double>> = X.getElems()
            for (i in 0 until n) {
                for (j in 0 until n) {
                    if (i < j) {
                        R[i][j] = QR[i][j]
                    } else if (i == j) {
                        R[i][j] = Rdiag[i]
                    } else {
                        R[i][j] = 0.0
                    }
                }
            }
            return X
        }

    /** Generate and return the orthogonal factor as [Matrix] output type.
     * @return     Q
     */
    val Q: Matrix
        get() {
            val X = Matrix(m, n)
            val Q: Array<Array<Double>> = X.getElems()
            for (k in n - 1 downTo 0) {
                for (i in 0 until m) {
                    Q[i][k] = 0.0
                }
                Q[k][k] = 1.0
                for (j in k until n) {
                    if (QR[k][k] != 0.0) {
                        var s = 0.0
                        for (i in k until m) {
                            s += QR[i][k] * Q[i][j]
                        }
                        s = -s / QR[k][k]
                        for (i in k until m) {
                            Q[i][j] += s * QR[i][k]
                        }
                    }
                }
            }
            return X
        }

    /** Least squares solution of A*X = B
     * @param B    A Matrix with as many rows as A and any number of columns.
     * @return     X that minimizes the two norm of Q*R*X-B.
     * @exception  IllegalArgumentException  Matrix row dimensions must agree.
     * @exception  RuntimeException  Matrix is rank deficient.
     */
    fun solve(B: Matrix): Matrix {
        require(B.getN() == m) { "Matrix row dimensions must agree." }
        if (!isFullRank) {
            throw RuntimeException("Matrix is rank deficient.")
        }

        // Copy right hand side
        val nx: Int = B.getM()
        val X: Array<Array<Double>> = B.getElems().clone()

        // Compute Y = transpose(Q)*B
        for (k in 0 until n) {
            for (j in 0 until nx) {
                var s = 0.0
                for (i in k until m) {
                    s += QR[i][k] * X[i][j]
                }
                s = -s / QR[k][k]
                for (i in k until m) {
                    X[i][j] += s * QR[i][k]
                }
            }
        }
        // Solve R*X = Y;
        for (k in n - 1 downTo 0) {
            for (j in 0 until nx) {
                X[k][j] /= Rdiag[k]
            }
            for (i in 0 until k) {
                for (j in 0 until nx) {
                    X[i][j] -= X[k][j] * QR[i][k]
                }
            }
        }
        return Matrix(X)
    }

    /** QR Decomposition, computed by Householder reflections.
     * Structure to access R and the Householder vectors and compute Q.
     * @param A Rectangular matrix
     */
    init {
        // Initialize.
        QR = matrix.getElems().clone()
        m = matrix.getN()
        n = matrix.getM()
        Rdiag = Array(n) { 0.0 }

        // Main loop.
        for (k in 0 until n) {
            // Compute 2-norm of k-th column without under/overflow.
            var nrm = 0.0
            for (i in k until m) {
                nrm = hypot(nrm, QR[i][k])
            }
            if (nrm != 0.0) {
                // Form k-th Householder vector.
                if (QR[k][k] < 0) {
                    nrm = -nrm
                }
                for (i in k until m) {
                    QR[i][k] /= nrm
                }
                QR[k][k] += 1.0

                // Apply transformation to remaining columns.
                for (j in k + 1 until n) {
                    var s = 0.0
                    for (i in k until m) {
                        s += QR[i][k] * QR[i][j]
                    }
                    s = -s / QR[k][k]
                    for (i in k until m) {
                        QR[i][j] += s * QR[i][k]
                    }
                }
            }
            Rdiag[k] = -nrm
        }
    }
}