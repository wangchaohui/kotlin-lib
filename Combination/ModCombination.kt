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
        fun from(value: Int) = ModInt(value.mod())
        fun from(value: Long) = ModInt(value.mod())
        fun Int.mod() = mod(MOD)
        fun Long.mod() = mod(MOD)
        val Zero = from(0)
        val One = from(1)
        const val MOD = 1000000007
    }
}

class ModCombination(n: Int) {
    val factorial = Array(n + 1) { ModInt.One }.apply {
        for (i in 1..n) this[i] = this[i - 1] * i
    }
    val factorialInv = Array(n + 1) { ModInt.One }.apply {
        this[n] = factorial[n].inv()
        for (i in n downTo 1) this[i - 1] = this[i] * i
    }

    fun c(a: Int, b: Int) =
        if (a >= b) factorial[a] * factorialInv[b] * factorialInv[a - b] else ModInt.Zero
}

val mc = ModCombination(400000)
