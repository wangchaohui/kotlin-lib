@JvmInline
value class ModInt private constructor(val value: Int) {
    operator fun plus(other: ModInt) = plus(other.value)
    operator fun plus(other: Int) = from(value + other)
    operator fun minus(other: ModInt) = minus(other.value)
    operator fun minus(other: Int) = from(value - other)
    operator fun times(other: ModInt) = times(other.value)
    operator fun times(other: Int) = from(value.toLong() * other)

    fun pow(exponent: Int): ModInt {
        var ans = One
        var a = this
        var b = exponent
        while (b > 0) {
            if (b % 2 == 1) ans *= a
            a *= a
            b /= 2
        }
        return ans
    }

    fun inv(): ModInt = pow(MOD - 2)

    companion object {
        fun from(value: Int) = ModInt(value.mod())
        fun from(value: Long) = ModInt(value.mod())
        fun Int.mod() = mod(MOD)
        fun Long.mod() = mod(MOD)
        val Zero = from(0)
        val One = from(1)
        const val MOD = 1000000007
    }
}

class Matrix(
    val n: Int,
    val m: Int,
    private val initialValue: ModInt = ModInt.Zero
) {
    val value = Array(n) { Array(m) { initialValue } }

    fun fill(list: List<Int>) {
        check(list.size == n * m)
        list.forEachIndexed { i, a ->
            value[i / m][i % m] = ModInt.from(a)
        }
    }

    operator fun times(other: Matrix): Matrix {
        check(m == other.n)
        val res = Matrix(n, other.m)
        for (i in 0..<n) {
            for (j in 0..<other.m) {
                for (k in 0..<m) {
                    res.value[i][j] += value[i][k] * other.value[k][j]
                }
            }
        }
        return res
    }

    fun pow(exponent: Long): Matrix {
        check(n == m)
        var ans = identity(n)
        var a = this
        var b = exponent
        while (b > 0) {
            if (b and 1 == 1L) ans *= a
            a *= a
            b /= 2
        }
        return ans
    }

    companion object {
        fun identity(n: Int) = Matrix(n, n).apply {
            for (i in 0..<n) value[i][i] = ModInt.One
        }
    }
}
