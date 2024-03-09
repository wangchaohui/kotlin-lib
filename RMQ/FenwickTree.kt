class FenwickTree(n: Int) {
    private val tree = LongArray(n)

    constructor(values: List<Long>) : this(values.size) {
        values.forEachIndexed { index, value ->
            parent(index)?.let { tree[it] += value }
        }
    }

    /** Gets the sum of `[0, index]`. */
    fun prefixSum(index: Int): Long {
        check(index in tree.indices)
        return generateSequence(index, ::lastTree).sumOf(tree::get)
    }

    /** Gets the sum of `[l, r]`. */
    fun rangeSum(l: Int, r: Int): Long = prefixSum(r) - if (l > 0) prefixSum(l - 1) else 0

    /** Gets the value at [index]. */
    operator fun get(index: Int): Long = rangeSum(index, index)

    fun add(index: Int, value: Long) {
        check(index in tree.indices)
        generateSequence(index, ::parent).forEach { tree[it] += value }
    }

    operator fun set(index: Int, value: Long) {
        add(index, value - this[index])
    }

    private fun lastTree(i: Int) = (i - (i + 1).takeLowestOneBit()).takeIf { it >= 0 }
    private fun parent(i: Int) = (i + (i + 1).takeLowestOneBit()).takeIf { it < tree.size }
}
