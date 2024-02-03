class ModInt(v: Int) {
    constructor(v: Long) : this(v.mod(MOD))

    private val value = v.mod(MOD)

    operator fun plus(other: ModInt) = plus(other.value)
    operator fun plus(other: Int) = ModInt(value + other)
    operator fun minus(other: ModInt) = minus(other.value)
    operator fun minus(other: Int) = ModInt(value - other)
    operator fun times(other: ModInt) = times(other.value)
    operator fun times(other: Int) = ModInt(value.toLong() * other)
    override fun toString() = value.toString()

    fun pow(pow: Int): ModInt {
        var a = this
        var b = pow
        var ans = ModInt(1)
        while (b > 0) {
            if (b % 2 == 1) ans *= a
            a *= a
            b /= 2
        }
        return ans
    }

    private companion object {
        const val MOD = 998244353
    }
}

fun Array<ModInt>.sum(): ModInt {
    var sum = ModInt(0)
    for (element in this) sum += element
    return sum
}
