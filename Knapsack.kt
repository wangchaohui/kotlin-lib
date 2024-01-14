class BitSet(size: Int) {
    private val n = (size - 1) / BITS_PER_WORD + 1
    private val longs = LongArray(n)

    operator fun get(index: Int): Boolean = (longs[index / BITS_PER_WORD] ushr index) and 1L > 0

    operator fun set(index: Int, value: Boolean) {
        longs[index / BITS_PER_WORD] = if (value) {
            longs[index / BITS_PER_WORD] or (1L shl index)
        } else {
            longs[index / BITS_PER_WORD] and (1L shl index).inv()
        }
    }

    fun or(other: BitSet) {
        for (i in 0..<min(longs.size, other.longs.size)) longs[i] = longs[i] or other.longs[i]
    }

    fun shlOr(bitCount: Int) {
        val x = bitCount / BITS_PER_WORD
        val y = bitCount % BITS_PER_WORD
        for (i in longs.lastIndex downTo x) {
            longs[i] = longs[i] or (longs[i - x] shl y) or
                    if (y > 0 && i - x - 1 >= 0) longs[i - x - 1] ushr (BITS_PER_WORD - y) else 0
        }
    }

    override fun toString() = (0..<n * BITS_PER_WORD).filter(::get).joinToString(prefix = "{", postfix = "}")

    private companion object {
        const val BITS_PER_WORD = 64
    }
}

fun knapsack(target: Int, items: List<Int>) = BitSet(target + 1).apply {
    this[0] = true
    for (item in items) shlOr(item)
}

fun knapsackSplit(target: Int, items: List<Int>): List<BitSet> {
    val dp = List(target + 1) { BitSet(target + 1) }
    dp[0][0] = true
    for (item in items) {
        for (i in target downTo 0) {
            dp.getOrNull(i + item)?.or(dp[i])
            dp[i].shlOr(item)
        }
    }
    return dp
}
