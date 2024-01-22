class SimpleSegmentTree(private val n: Int) {
    data class Interval(
        val sum: Long = 0,
        val maxPrefixSum: Long = sum,
        val maxSuffixSum: Long = sum,
        val maxSubArraySum: Long = sum,
    ) {
        companion object {
            fun combineNullable(l: Interval?, r: Interval?): Interval? = when {
                l == null -> r
                r == null -> l
                else -> combine(l, r)
            }

            fun combine(l: Interval, r: Interval) = Interval(
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

    private val t = Array(n * 2) { Interval() }

    fun query(l: Int, r: Int): Interval? {
        var resL: Interval? = null
        var resR: Interval? = null
        var i = l + n
        var j = r + n
        while (i < j) {
            if (i and 1 > 0) resL = Interval.combineNullable(resL, t[i++])
            if (j and 1 > 0) resR = Interval.combineNullable(t[--j], resR)
            i /= 2
            j /= 2
        }
        return Interval.combineNullable(resL, resR)
    }

    fun update(p: Int, delta: Int) {
        var i = p + n
        t[i] = Interval(t[i].sum + delta)
        while (i > 1) {
            i /= 2
            t[i] = Interval.combine(t[i * 2], t[i * 2 + 1])
        }
    }
}
