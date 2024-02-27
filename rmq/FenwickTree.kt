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
