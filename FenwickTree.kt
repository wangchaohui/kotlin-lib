class FenwickTree(values: List<Int>) {
    private val tree = values.toMutableList()

    init {
        tree.forEachIndexed { index, value ->
            val nextParent = index.nextParent()
            if (nextParent < tree.size) {
                tree[nextParent] += value
            }
        }
    }

    fun query(index: Int): Int {
        check(index in tree.indices)
        var sum = 0
        var p = index
        while (p >= 0) {
            sum += tree[p]
            p = p.parent()
        }
        return sum
    }

    fun update(index: Int, value: Int) {
        check(index in tree.indices)
        var np = index
        while (np < tree.size) {
            tree[np] += value
            np = np.nextParent()
        }
    }

    private companion object {
        fun Int.parent() = this - (this + 1).takeLowestOneBit()
        fun Int.nextParent() = this + (this + 1).takeLowestOneBit()
    }
}
