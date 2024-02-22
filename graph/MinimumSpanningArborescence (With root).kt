class MinimumSpanningArborescence(
    private val vertexSize: Int,
    private val strategy: DistanceCombineStrategy,
) {
    /** distances[i][j] = distance from j to i */
    val distances = Array(vertexSize) { LongArray(vertexSize) { Long.MAX_VALUE } }

    sealed interface DistanceCombineStrategy {
        fun combine(a: Long, b: Long): Long
        fun split(longer: Long, shorter: Long): Long
    }

    object Max : DistanceCombineStrategy {
        override fun combine(a: Long, b: Long) = max(a, b)
        override fun split(longer: Long, shorter: Long) = longer
    }

    object Add : DistanceCombineStrategy {
        override fun combine(a: Long, b: Long) = a + b
        override fun split(longer: Long, shorter: Long) =
            if (longer == Long.MAX_VALUE) Long.MAX_VALUE else longer - shorter
    }

    private val parent = IntArray(vertexSize) { it }
    var ans = 0L

    private fun getMinimumIncomingEdge(to: Int) = distances[to].withIndex().minBy { it.value }

    private tailrec fun addEdge(to: Int): Boolean {
        assert(parent[to] == to)
        val (index, value) = getMinimumIncomingEdge(to)
        if (value == Long.MAX_VALUE) return false
        parent[to] = index
        ans = strategy.combine(ans, value)
        val cycle = BooleanArray(vertexSize)
        var at = to
        while (!cycle[at]) {
            cycle[at] = true
            at = parent[at]
        }
        if (at == to) return addEdge(compress(cycle))
        return true
    }

    private fun compress(cycle: BooleanArray): Int {
        val (onCycleIds, offCycleIds) = cycle.indices.partition { cycle[it] }
        assert(onCycleIds.size >= 2)
        val leader = onCycleIds[0]
        for (i in offCycleIds) {
            distances[i][leader] = onCycleIds.minOf { j -> distances[i][j].also { distances[i][j] = Long.MAX_VALUE } }
            distances[leader][i] = onCycleIds.minOf { j ->
                strategy.split(distances[j][i], distances[j][parent[j]]).also { distances[j][i] = Long.MAX_VALUE }
            }
        }
        for (i in cycle.indices) if (cycle[parent[i]]) parent[i] = leader
        for (i in onCycleIds) distances[leader][i] = Long.MAX_VALUE
        return leader
    }

    fun solve(root: Int): Boolean = (0 until vertexSize).all { i -> i == root || addEdge(i) }
}
