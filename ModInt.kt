@JvmInline
value class ModInt private constructor(val value: Int) {
    operator fun plus(other: ModInt) = plus(other.value)
    operator fun plus(other: Int) = from(value + other)
    operator fun minus(other: ModInt) = minus(other.value)
    operator fun minus(other: Int) = from(value - other)
    operator fun times(other: ModInt) = times(other.value)
    operator fun times(other: Int) = from(value.toLong() * other)
    operator fun div(other: ModInt) = times(other.inv())
    operator fun div(other: Int) = div(from(other))

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

    override fun toString() = value.toString()

    companion object {
        fun combination(n: Int, k: Int): ModInt {
            check(k in 0..n)
            return (1..k).fold(One) { acc, i -> acc * (n - i + 1) / i }
        }

        fun from(value: Int) = ModInt(value.mod())
        fun from(value: Long) = ModInt(value.mod())
        fun Int.mod() = mod(MOD)
        fun Long.mod() = mod(MOD)
        val Zero = from(0)
        val One = from(1)

        const val MOD = 998244353
        const val MOD = 1000000007
    }
}

fun Array<ModInt>.sum(): ModInt {
    var sum = ModInt.Zero
    for (element in this) sum += element
    return sum
}
