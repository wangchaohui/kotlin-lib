class ModInt(private var value: Int) {
    constructor(value: Long) : this(value.mod(MOD))

    init {
        value = value.mod(MOD)
    }

    operator fun plus(other: ModInt) = plus(other.value)
    operator fun plus(other: Int) = ModInt(value + other)
    operator fun minus(other: ModInt) = minus(other.value)
    operator fun minus(other: Int) = ModInt(value - other)
    operator fun times(other: ModInt) = times(other.value)
    operator fun times(other: Int) = ModInt(value.toLong() * other)
    override fun toString() = value.toString()

    private companion object {
        const val MOD = 998244353
    }
}
