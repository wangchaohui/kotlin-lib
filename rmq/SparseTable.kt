class SparseTable<T>(list: List<T>, private val merge: (T, T) -> T) {
    private val n = list.size
    private val logN = n.takeHighestOneBit().countTrailingZeroBits()
    private val table = mutableListOf(list.toList()).apply {
        for (i in 1..logN) {
            val lastRangeSize = 1 shl i - 1
            add(List(n - lastRangeSize * 2 + 1) { j ->
                merge(this[i - 1][j], this[i - 1][j + lastRangeSize])
            })
        }
    }

    private val logs = IntArray(n).apply {
        this[1] = 0
        for (i in 2..<size) this[i] = this[i / 2] + 1
    }

    /** Query for range [l, r] */
    fun query(l: Int, r: Int): T {
        val log = logs[r - l]
        return merge(table[log][l], table[log][r - (1 shl log) + 1])
    }
}
