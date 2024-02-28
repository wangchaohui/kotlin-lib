class SimpleSegmentTree<T>(
    private val n: Int,
    private val identity: T,
    private val combine: (T, T) -> T,
) {
    private val tree = MutableList(2 * n) { identity }

    constructor(values: List<T>, identity: T, combine: (T, T) -> T) :
            this(values.size, identity, combine) {
        values.forEachIndexed { i, v -> tree[i + n] = v }
        for (i in n - 1 downTo 1) tree[i] = combine(tree[i * 2], tree[i * 2 + 1])
    }

    /** Queries for the range `[l, r)`. */
    fun query(l: Int, r: Int): T {
        var resL = identity
        var resR = identity
        var i = l + n
        var j = r + n
        while (i < j) {
            if (i and 1 > 0) resL = combine(resL, tree[i++])
            if (j and 1 > 0) resR = combine(tree[--j], resR)
            i /= 2
            j /= 2
        }
        return combine(resL, resR)
    }

    fun update(p: Int, transform: (T) -> T) {
        var i = p + n
        tree[i] = transform(tree[i])
        while (i > 1) {
            i /= 2
            tree[i] = combine(tree[i * 2], tree[i * 2 + 1])
        }
    }
}

val maxTree = SimpleSegmentTree(n = 500000, identity = 0, combine = ::max)

val maxSubArrayTree = SimpleSegmentTree(n = 500000, identity = Interval(0)) { l, r ->
    Interval(
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
