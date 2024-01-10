class FenwickTree(values: List<Int>) {
    private val tree = values.toMutableList()

    init {
        tree.forEachIndexed { index, value ->
            parent(index)?.let { tree[it] += value }
        }
    }

    operator fun get(index: Int): Int {
        check(index in tree.indices)
        return generateSequence(index, ::lastTree).sumOf(tree::get)
    }

    fun update(index: Int, value: Int) {
        check(index in tree.indices)
        generateSequence(index, ::parent).forEach { tree[it] += value }
    }

    private fun lastTree(i: Int) = (i - (i + 1).takeLowestOneBit()).takeIf { it >= 0 }
    private fun parent(i: Int) = (i + (i + 1).takeLowestOneBit()).takeIf { it < tree.size }
}
