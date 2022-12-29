package com.github.varenytsiamykhailo.knml.util

import java.io.Serializable
import kotlin.math.*


/** Singular Value Decomposition.
 * <P>
 * For an m-by-n matrix A with m >= n, the singular value decomposition is
 * an m-by-n orthogonal matrix U, an n-by-n diagonal matrix S, and
 * an n-by-n orthogonal matrix V so that A = U*S*V'.
</P> * <P>
 * The singular values, sigma[k] = S[k][k], are ordered so that
 * sigma[0] >= sigma[1] >= ... >= sigma[n-1].
</P> * <P>
 * The singular value decompostion always exists, so the constructor will
 * never fail.  The matrix condition number and the effective numerical
 * rank can be computed from this decomposition.
</P> */
class SVD(matrix: Matrix) : Serializable {

    /** Arrays for internal storage of U and V.
     * @serial internal storage of U.
     * @serial internal storage of V.
     */
    private val U: Matrix
    private val V: Matrix
    /** Return the one-dimensional array of singular values
     * @return     diagonal of S.
     */
    /** Array for internal storage of singular values.
     * @serial internal storage of singular values.
     */
    val singularValues: DoubleArray

    /** Row and column dimensions.
     * @serial row dimension.
     * @serial column dimension.
     */
    private val n: Int
    private val m: Int

    /** Construct the singular value decomposition
     * @param A    Rectangular matrix
     * @return     Structure to access U, S and V.
     */
    init {
        val A = Matrix(matrix.getElems())
        n = matrix.getN()
        m = matrix.getM()

        /* Apparently the failing cases are only a proper subset of (m<n),
	 so let's not throw error.  Correct fix to come later?
      if (m<n) {
	  throw new IllegalArgumentException("Jama SVD only works for m >= n"); }
      */
        val nu = min(n, m)
        println("N,m = $n, $m")
        singularValues = DoubleArray(min(n + 1, m))
        U = Matrix(n, nu)
        V = Matrix(m, m)
        val e = DoubleArray(m)
        val work = DoubleArray(n)
        val wantu = true
        val wantv = true

        // Reduce A to bidiagonal form, storing the diagonal elements
        // in s and the super-diagonal elements in e.
        val nct = min(n - 1, m)
        val nrt = max(0, min(m - 2, n))
        for (k in 0 until max(nct, nrt)) {
            if (k < nct) {

                // Compute the transformation for the k-th column and
                // place the k-th diagonal in s[k].
                // Compute 2-norm of k-th column without under/overflow.
                singularValues[k] = 0.0
                for (i in k until n) {
                    singularValues[k] = hypot(singularValues[k], A.getElem(i, k))
                }
                if (singularValues[k] != 0.0) {
                    if (A.getElem(k, k) < 0.0) {
                        singularValues[k] = -singularValues[k]
                    }
                    for (i in k until n) {
                        A.setElem(i, k, A.getElem(i, k) / singularValues[k])
                    }
                    A.setElem(k, k, A.getElem(k, k) + 1.0)
                }
                singularValues[k] = -singularValues[k]
            }
            for (j in k + 1 until m) {
                if (k < nct && singularValues[k] != 0.0) {

                    // Apply the transformation.
                    var t = 0.0
                    for (i in k until n) {
                        t += A.getElem(i, k) * A.getElem(i, j)
                    }
                    t = -t / A.getElem(k, k)
                    for (i in k until n) {
                        A.setElem(i, j, A.getElem(i, j) + t * A.getElem(i, k))
                    }
                }

                // Place the k-th row of A into e for the
                // subsequent calculation of the row transformation.
                e[j] = A.getElem(k, j)
            }
            if (wantu && k < nct) {

                // Place the transformation in U for subsequent back
                // multiplication.
                for (i in k until n) {
                    U.setElem(i, k, A.getElem(i, k))
                }
            }
            if (k < nrt) {

                // Compute the k-th row transformation and place the
                // k-th super-diagonal in e[k].
                // Compute 2-norm without under/overflow.
                e[k] = 0.0
                for (i in k + 1 until m) {
                    e[k] = hypot(e[k], e[i])
                }
                if (e[k] != 0.0) {
                    if (e[k + 1] < 0.0) {
                        e[k] = -e[k]
                    }
                    for (i in k + 1 until m) {
                        e[i] /= e[k]
                    }
                    e[k + 1] += 1.0
                }
                e[k] = -e[k]
                if (k + 1 < n && e[k] != 0.0) {

                    // Apply the transformation.
                    for (i in k + 1 until n) {
                        work[i] = 0.0
                    }
                    for (j in k + 1 until m) {
                        for (i in k + 1 until n) {
                            work[i] += e[j] * A.getElem(i, j)
                        }
                    }
                    for (j in k + 1 until m) {
                        val t = -e[j] / e[k + 1]
                        for (i in k + 1 until n) {
                            A.setElem(i, j, A.getElem(i, j) + t * work[i])
                        }
                    }
                }
                if (wantv) {
                    // Place the transformation in V for subsequent
                    // back multiplication.
                    for (i in k + 1 until m) {
                        V.setElem(i, k, e[i])
                    }
                }
            }
        }

        // Set up the final bidiagonal matrix or order p.
        var p = min(m, n + 1)
        if (nct < m) {
            singularValues[nct] = A.getElem(nct, nct)
        }
        if (n < p) {
            singularValues[p - 1] = 0.0
        }
        if (nrt + 1 < p) {
            e[nrt] = A.getElem(nct, p - 1)
        }
        e[p - 1] = 0.0

        // If required, generate U.
        if (wantu) {
            for (j in nct until nu) {
                for (i in 0 until n) {
                    U.setElem(i, j, 0.0)
                }
                U.setElem(j, j, 1.0)
            }
            for (k in nct - 1 downTo 0) {
                if (singularValues[k] != 0.0) {
                    for (j in k + 1 until nu) {
                        var t = 0.0
                        for (i in k until n) {
                            t += U.getElem(i, k) * U.getElem(i, j)
                        }
                        t = -t / U.getElem(k, k)
                        for (i in k until n) {
                            U.setElem(i, j, U.getElem(i, j) + t * U.getElem(i, k))
                        }
                    }
                    for (i in k until n) {
                        U.setElem(i, k, -U.getElem(i, k))
                    }
                    U.setElem(k, k, 1.0 + U.getElem(k, k))
                    for (i in 0 until k - 1) {
                        U.setElem(i, k, 0.0)
                    }
                } else {
                    for (i in 0 until n) {
                        U.setElem(i, k, 0.0)
                    }
                    U.setElem(k, k, 1.0)
                }
            }
        }

        // If required, generate V.
        if (wantv) {
            for (k in m - 1 downTo 0) {
                if (k < nrt && e[k] != 0.0) {
                    for (j in k + 1 until nu) {
                        var t = 0.0
                        for (i in k + 1 until m) {
                            t += V.getElem(i, k) * V.getElem(i, j)
                        }
                        t = -t / V.getElem(k + 1, k)
                        for (i in k + 1 until m) {
                            V.setElem(i, j, V.getElem(i, j) + t * V.getElem(i, k))
                        }
                    }
                }
                for (i in 0 until m) {
                    V.setElem(i, k, 0.0)
                }
                V.setElem(k, k, 1.0)
            }
        }

        // Main iteration loop for the singular values.
        val pp = p - 1
        var iter = 0
        val eps = 2.0.pow(-52.0)
        val tiny = 2.0.pow(-966.0)
        while (p > 0) {
            var k: Int
            var case: Int

            // Here is where a test for too many iterations would go.

            // This section of the program inspects for negligible elements in the s and e arrays.
            // On completion the variables case and k are set as follows.

            // case = 1     if s(p) and e[k-1] are negligible and k<p
            // case = 2     if s(k) is negligible and k<p
            // case = 3     if e[k-1] is negligible, k<p, and
            //              s(k), ..., s(p) are not negligible (qr step).
            // case = 4     if e(p-1) is negligible (convergence).
            k = p - 2
            while (k >= -1) {
                if (k == -1) {
                    break
                }
                if (abs(e[k]) <= tiny + eps * (abs(singularValues[k]) + abs(singularValues[k + 1]))
                ) {
                    e[k] = 0.0
                    break
                }
                k--
            }
            if (k == p - 2) {
                case = 4
            } else {
                var ks: Int = p - 1
                while (ks >= k) {
                    if (ks == k) {
                        break
                    }
                    val t = (if (ks != p) abs(e[ks]) else 0.0) +
                            if (ks != k + 1) abs(e[ks - 1]) else 0.0
                    if (abs(singularValues[ks]) <= tiny + eps * t) {
                        singularValues[ks] = 0.0
                        break
                    }
                    ks--
                }
                when (ks) {
                    k -> {
                        case = 3
                    }
                    p - 1 -> {
                        case = 1
                    }
                    else -> {
                        case = 2
                        k = ks
                    }
                }
            }
            k++
            when (case) {
                1 -> {
                    var f = e[p - 2]
                    e[p - 2] = 0.0
                    var j = p - 2
                    while (j >= k) {
                        var t: Double = hypot(singularValues[j], f)
                        val cs = singularValues[j] / t
                        val sn = f / t
                        singularValues[j] = t
                        if (j != k) {
                            f = -sn * e[j - 1]
                            e[j - 1] = cs * e[j - 1]
                        }
                        if (wantv) {
                            var i = 0
                            while (i < m) {
                                t = cs * V.getElem(i, j) + sn * V.getElem(i, p - 1)
                                V.setElem(i, p - 1, -sn * V.getElem(i, j) + cs * V.getElem(i, p - 1))
                                V.setElem(i, j, t)
                                i++
                            }
                        }
                        j--
                    }
                }

                2 -> {
                    var f = e[k - 1]
                    e[k - 1] = 0.0
                    var j = k
                    while (j < p) {
                        var t: Double = hypot(singularValues[j], f)
                        val cs = singularValues[j] / t
                        val sn = f / t
                        singularValues[j] = t
                        f = -sn * e[j]
                        e[j] = cs * e[j]
                        if (wantu) {
                            var i = 0
                            while (i < n) {
                                t = cs * U.getElem(i, j) + sn * U.getElem(i, k - 1)
                                U.setElem(i, k - 1, -sn * U.getElem(i, j) + cs * U.getElem(i, k - 1))
                                U.setElem(i, j, t)
                                i++
                            }
                        }
                        j++
                    }
                }

                3 -> {
                    // Calculate the shift.
                    val scale = max(
                        max(
                            max(
                                max(
                                    abs(singularValues[p - 1]),
                                    abs(singularValues[p - 2])
                                ),
                                abs(e[p - 2])
                            ),
                            abs(singularValues[k])
                        ),
                        abs(e[k])
                    )
                    val sp = singularValues[p - 1] / scale
                    val spm1 = singularValues[p - 2] / scale
                    val epm1 = e[p - 2] / scale
                    val sk = singularValues[k] / scale
                    val ek = e[k] / scale
                    val b = ((spm1 + sp) * (spm1 - sp) + epm1 * epm1) / 2.0
                    val c = sp * epm1 * (sp * epm1)
                    var shift = 0.0
                    if ((b != 0.0) or (c != 0.0)) {
                        shift = sqrt(b * b + c)
                        if (b < 0.0) {
                            shift = -shift
                        }
                        shift = c / (b + shift)
                    }
                    var f = (sk + sp) * (sk - sp) + shift
                    var g = sk * ek

                    // Chase zeros.
                    var j = k
                    while (j < p - 1) {
                        var t: Double = hypot(f, g)
                        var cs = f / t
                        var sn = g / t
                        if (j != k) {
                            e[j - 1] = t
                        }
                        f = cs * singularValues[j] + sn * e[j]
                        e[j] = cs * e[j] - sn * singularValues[j]
                        g = sn * singularValues[j + 1]
                        singularValues[j + 1] = cs * singularValues[j + 1]
                        if (wantv) {
                            var i = 0
                            while (i < m) {
                                t = cs * V.getElem(i, j) + sn * V.getElem(i, j + 1)
                                V.setElem(i, j + 1, -sn * V.getElem(i, j) + cs * V.getElem(i, j + 1))
                                V.setElem(i, j, t)
                                i++
                            }
                        }
                        t = hypot(f, g)
                        cs = f / t
                        sn = g / t
                        singularValues[j] = t
                        f = cs * e[j] + sn * singularValues[j + 1]
                        singularValues[j + 1] = -sn * e[j] + cs * singularValues[j + 1]
                        g = sn * e[j + 1]
                        e[j + 1] = cs * e[j + 1]
                        if (wantu && j < n - 1) {
                            var i = 0
                            while (i < n) {
                                t = cs * U.getElem(i, j) + sn * U.getElem(i, j + 1)
                                U.setElem(i, j + 1, -sn * U.getElem(i, j) + cs * U.getElem(i, j + 1))
                                U.setElem(i, j, t)
                                i++
                            }
                        }
                        j++
                    }
                    e[p - 2] = f
                    iter += 1
                }

                4 -> {
                    // Make the singular values positive.
                    if (singularValues[k] <= 0.0) {
                        singularValues[k] = if (singularValues[k] < 0.0) -singularValues[k] else 0.0
                        if (wantv) {
                            var i = 0
                            while (i <= pp) {
                                V.setElem(i, k, -V.getElem(i, k))
                                i++
                            }
                        }
                    }

                    // Order the singular values.
                    while (k < pp) {
                        if (singularValues[k] >= singularValues[k + 1]) {
                            break
                        }
                        var t = singularValues[k]
                        singularValues[k] = singularValues[k + 1]
                        singularValues[k + 1] = t
                        if (wantv && k < m - 1) {
                            var i = 0
                            while (i < m) {
                                t = V.getElem(i, k + 1)
                                V.setElem(i, k + 1, V.getElem(i, k))
                                V.setElem(i, k, t)
                                i++
                            }
                        }
                        if (wantu && k < n - 1) {
                            var i = 0
                            while (i < n) {
                                t = U.getElem(i, k + 1)
                                U.setElem(i, k + 1, U.getElem(i, k))
                                U.setElem(i, k, t)
                                i++
                            }
                        }
                        k++
                    }
                    iter = 0
                    p--
                }
            }
        }
    }

    /** Return the left singular vectors
     * @return     U
     */
    fun getU(): Matrix {
        return U
    }

    /** Return the right singular vectors
     * @return     V
     */
    fun getV(): Matrix {
        return V
    }

    /** Return the diagonal matrix of singular values
     * @return     S
     */
    fun getS(): Matrix {
        val X = Matrix(m, m)
        println("Singular values: " + singularValues.size)
        val S: Array<Array<Double>> = X.getElems()
        for (i in 0 until m) {
            for (j in 0 until m) {
                S[i][j] = 0.0
            }
            S[i][i] = singularValues[i]
        }
        return X
    }

    /** Two norm
     * @return     max(S)
     */
    fun norm2(): Double {
        return singularValues[0]
    }

    /** Two norm condition number
     * @return     max(S)/min(S)
     */
    fun cond(): Double {
        return singularValues[0] / singularValues[min(n, m) - 1]
    }

    /** Effective numerical matrix rank
     * @return     Number of non-negligible singular values.
     */
    fun rank(): Int {
        val eps = 2.0.pow(-52.0)
        val tol = max(n, m) * singularValues[0] * eps
        var r = 0
        for (i in singularValues.indices) {
            if (singularValues[i] > tol) {
                r++
            }
        }
        return r
    }
}