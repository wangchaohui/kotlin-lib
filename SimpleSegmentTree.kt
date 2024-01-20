
class SimpleSegmentTree(private val values: LongArray) {
    data class Interval(
        val sum: Long = 0,
        val maxPrefixSum: Long = sum,
        val maxSuffixSum: Long = sum,
        val maxSubArraySum: Long = sum,
    ) {
        companion object {
            fun mergeNullable(l: Interval?, r: Interval?): Interval? = when {
                l == null -> r
                r == null -> l
                else -> merge(l, r)
            }

            fun merge(l: Interval, r: Interval) = Interval(
                sum = l.sum + r.sum,
                maxPrefixSum = max(l.maxPrefixSum, l.sum + r.maxPrefixSum),
                maxSuffixSum = max(r.maxSuffixSum, l.maxSuffixSum + r.sum),
                maxSubArraySum = maxOf(
                    l.maxSubArraySum,
                    r.maxSubArraySum,
                    l.maxSuffixSum + r.maxPrefixSum,
                ),
            )
        }
    }

    private fun treeSize() = (values.size - 1).takeHighestOneBit() * 4 - 1
    private val tree = Array(treeSize()) { Interval() }

    init {
        fun buildDfs(index: Int, l: Int, r: Int): Interval =
            if (l == r) {
                Interval(values[l])
            } else {
                val m = (l + r) / 2
                Interval.merge(
                    buildDfs(index * 2 + 1, l, m),
                    buildDfs(index * 2 + 2, m + 1, r),
                )
            }.also { tree[index] = it }
        buildDfs(0, 0, values.lastIndex)
    }

    fun query(ql: Int, qr: Int): Interval? {
        fun queryDfs(index: Int, l: Int, r: Int): Interval? = when {
            l > qr || r < ql -> null
            l >= ql && r <= qr -> tree[index]
            else -> {
                val m = (l + r) / 2
                Interval.mergeNullable(
                    queryDfs(index * 2 + 1, l, m),
                    queryDfs(index * 2 + 2, m + 1, r),
                )
            }
        }
        return queryDfs(0, 0, values.lastIndex)
    }

    fun update(x: Int, delta: Int) {
        fun updateDfs(index: Int, l: Int, r: Int): Interval = when {
            x !in l..r -> tree[index]
            l == r -> Interval(tree[index].sum + delta)
            else -> {
                val m = (l + r) / 2
                Interval.merge(
                    updateDfs(index * 2 + 1, l, m),
                    updateDfs(index * 2 + 2, m + 1, r),
                )
            }
        }.also { tree[index] = it }
        updateDfs(0, 0, values.lastIndex)
    }
}
