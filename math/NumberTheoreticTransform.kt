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
        const val MOD = 998244353  // prime, 7 * 17 * 2^23 + 1
    }
}

class NumberTheoreticTransform {

    private val rev = IntArray(N).apply {
        for (i in 1..<N) {
            this[i] = this[i / 2] / 2
            if (i and 1 == 1) {
                this[i] += N / 2
            }
        }
    }

    fun ntt(p: Array<ModInt>, inverse: Boolean = false) {
        prepare(p)
        var subProblemSize = 2
        while (subProblemSize <= N) {
            val gn = G.pow((ModInt.MOD - 1) / subProblemSize)
            for (subProblemStart in 0..<N step subProblemSize) {
                var g = ModInt.One
                for (j in subProblemStart..<subProblemStart + subProblemSize / 2) {
                    val u = p[j]
                    val t = g * p[j + subProblemSize / 2]
                    p[j] = u + t
                    p[j + subProblemSize / 2] = u - t
                    g *= gn
                }
            }
            subProblemSize *= 2
        }
        if (inverse) {
            p.reverse(1, N)
            for (j in 0..<N) p[j] *= InvN
        }
    }

    private fun prepare(x: Array<ModInt>) {
        for (i in 1..<N) {
            if (i < rev[i]) x[i] = x[rev[i]].also { x[rev[i]] = x[i] }
        }
    }

    companion object {
        const val LOG_N = 20
        const val N = 1 shl LOG_N
        private val InvN = ModInt.from(N).inv()
        private val G = ModInt.from(3)
    }
}
