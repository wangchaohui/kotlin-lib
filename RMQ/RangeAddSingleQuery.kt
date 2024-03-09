typealias NumberType = Long

class FenwickTree(values: List<NumberType>) {
    private val tree = values.toMutableList()

    init {
        tree.forEachIndexed { index, value ->
            parent(index)?.let { tree[it] += value }
        }
    }

    /** Gets the sum of `[0, index]`. */
    operator fun get(index: Int): NumberType {
        check(index in tree.indices)
        return generateSequence(index, ::lastTree).sumOf(tree::get)
    }

    fun add(index: Int, value: NumberType) {
        check(index in tree.indices)
        generateSequence(index, ::parent).forEach { tree[it] += value }
    }

    private fun lastTree(i: Int) = (i - (i + 1).takeLowestOneBit()).takeIf { it >= 0 }
    private fun parent(i: Int) = (i + (i + 1).takeLowestOneBit()).takeIf { it < tree.size }
}

class RangeAddSingleQuery(private val values: List<NumberType>) {
    private val tree =
        FenwickTree(values.mapIndexed { index, i -> i - values.getOrElse(index - 1) { 0 } })

    operator fun get(index: Int) = tree[index]
    fun add(index: Int, value: NumberType) = addRange(index, index + 1, value)

    /** Adds the [value] to `[l, r)`. */
    fun addRange(l: Int, r: Int, value: NumberType) {
        if (l < values.size) tree.add(l, value)
        if (r < values.size) tree.add(r, -value)
    }
}
