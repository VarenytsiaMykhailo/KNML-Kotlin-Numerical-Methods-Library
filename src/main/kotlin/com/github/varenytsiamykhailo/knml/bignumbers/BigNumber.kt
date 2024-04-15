package com.github.varenytsiamykhailo.knml.bignumbers

class BigNumber(val number: String) {
    fun haveMinus(): Boolean {
        return number.startsWith("-")
    }

    /**
     * BigNumber multiplication BigNumber
     *
     * @param [y] is another [BigNumber].
     *
     * @return This method returns multiplication of value [y] and [BigNumber] type of [BigNumber].
     */
    fun mul(y: BigNumber): BigNumber {
        var a = this
        var b = y
        var wasMinus = false

        if (this.haveMinus() && y.haveMinus()) {
            a = BigNumber(number.drop(1))
            b = BigNumber(y.number.drop(1))
        } else if (this.haveMinus()) {
            wasMinus = true
            a = BigNumber((number.drop(1)))
        } else if (y.haveMinus()) {
            wasMinus = true
            b = BigNumber(y.number.drop(1))
        }

        val ans = a.multiplyWithoutMinus(b).dropFirstZeros()

        if (wasMinus) {
            return BigNumber("-" + ans.number)
        }

        return ans
    }

    private fun multiplyWithoutMinus(y: BigNumber): BigNumber {
        val first: String
        val second: Int

        if (this.number.length < 2) {
            first = y.number
            second = this.number.toInt()
        } else {
            first = this.number
            second = y.number.toInt()
        }

        var b = 0
        var result = BigNumber("0")
        for (i in first.length-1 downTo  0) {
            val n = first.drop(i).take(1).toInt()
            val mul = n * second
            result += BigNumber(mul.toString()).multiplyByPower(b)

            b++
        }

        return result
    }

    fun multiplyByPower(power: Int): BigNumber {
        return BigNumber(number + "0".repeat(power))
    }

    fun bigger(y: BigNumber): Boolean {
        // Если длина первого числа больше второго, возвращаем 1
        if (number.length > y.number.length) return true

        // Если длина второго числа больше первого, возвращаем -1
        if (number.length < y.number.length) return false

        // Если длины чисел равны, сравниваем их посимвольно
        for (i in number.indices) {
            val digit1 = number[i]
            val digit2 = y.number[i]

            if (digit1 > digit2) return true
            if (digit1 < digit2) return false
        }

        // Если все цифры равны, числа равны
        return false
    }

    operator fun plus(y: BigNumber): BigNumber {
        return if (this.haveMinus()) {
            if (y.haveMinus()) {
                // Both numbers are negative - perform addition and return negative result
                val a = BigNumber(number.drop(1))
                val b = BigNumber(y.number.drop(1))
                BigNumber("-" + (a + b).number)
            } else {
                // x is negative, y is positive - perform subtraction
                val a = BigNumber(number.drop(1))
                y - a
            }
        } else {
            if (y.haveMinus()) {
                // x is positive, y is negative - perform subtraction
                val a = BigNumber(y.number.drop(1))
                this - a
            } else {
                // Both numbers are positive - perform addition
                val maxLength = kotlin.math.max(number.length, y.number.length)
                val result = StringBuilder(maxLength)

                var carry = 0
                for (i in 1..maxLength) {
                    val digit1 = if (i <= number.length) number[number.length - i] - '0' else 0
                    val digit2 =
                            if (i <= y.number.length) y.number[y.number.length - i] - '0' else 0

                    val sum = digit1 + digit2 + carry
                    carry = sum / 10
                    result.append((sum % 10).toString())
                }

                if (carry != 0) BigNumber(result.append(carry.toString()).toString())
                BigNumber(result.toString().reversed())
            }
        }
    }

    operator fun minus(y: BigNumber): BigNumber {
        if (this.haveMinus() && y.haveMinus()) {
            val a = BigNumber(number.drop(1))
            val b = BigNumber(y.number.drop(1))
            return b - a
        } else if (this.haveMinus() && !y.haveMinus()) {
            val a = BigNumber(number.drop(1))

            return BigNumber("-" + (a + y).number)
        } else if (!this.haveMinus() && y.haveMinus()) {
            // x is positive, y is negative - perform addition
            val b = BigNumber(y.number.drop(1))

            return this + b
        }

        if (y.bigger(this)) {
            val maxLength = y.number.length
            val result = StringBuilder(maxLength)

            var carry = 0

            for (i in 1..maxLength) {
                val digit1 = if (i <= y.number.length) y.number[y.number.length - i] - '0' else 0
                val digit2 =
                        carry + if (i <= number.length) number[number.length - i] - '0' else 0

                var tmp = digit1 - digit2
                carry = 0
                if (tmp < 0) {
                    tmp += 10
                    carry = 1
                }

                result.append(tmp.toString())
            }

            return BigNumber("-" + result.toString().reversed().trimStart('0'))
        }
        // Both numbers are positive - perform subtraction
        val maxLength = number.length
        val result = StringBuilder(maxLength)

        var carry = 0

        for (i in 1..maxLength) {
            val digit1 = if (i <= number.length) number[number.length - i] - '0' else 0
            val digit2 =
                    carry + if (i <= y.number.length) y.number[y.number.length - i] - '0' else 0

            var tmp = digit1 - digit2
            carry = 0
            if (tmp < 0) {
                tmp += 10
                carry = 1
            }

            result.append(tmp.toString())
        }

        return BigNumber(result.toString().reversed().trimStart('0'))
    }

    fun getParts(r: Int, m: Int): MutableList<BigNumber> {
        var value = this.number
        var parts = MutableList(r) {BigNumber("0")}

        for (i in 0 until r) {
            if (value.length <= m) {
                parts[i] = BigNumber(value)
                break
            }

            parts[i] = BigNumber(value.takeLast(m))
            value = value.dropLast(m)

        }

        return parts
    }

    fun dropFirstZeros(): BigNumber {
        val res = this.number.dropWhile{
            it == '0'
        }

        if (res.length == 0) {
            return BigNumber("0")
        }

        return BigNumber(res)
    }
}
