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

    private val incomingEdge = Array(vertexSize) { IndexedValue(it, 0L) } // from to distance
    private var savedTo = 0
    private var savedEdge = IndexedValue(0, 0L)
    var ans = 0L

    private fun getMinimumIncomingEdge(i: Int) = distances[i].withIndex().minBy { it.value }

    private fun addEdge(i: Int) {
        assert(incomingEdge[i].index == i)
        val edge = getMinimumIncomingEdge(i)
        var nextTo = i
        var nextEdge = edge
        if (savedEdge.value < edge.value) {
            nextTo = savedTo
            nextEdge = savedEdge
            savedTo = i
            savedEdge = edge
        }
        addEdge(nextTo, nextEdge)
    }

    private fun addEdge(to: Int, edge: IndexedValue<Long>) {
        incomingEdge[to] = edge
        ans = strategy.combine(ans, edge.value)
        val cycle = BooleanArray(vertexSize)
        var at = to
        while (!cycle[at]) {
            cycle[at] = true
            at = incomingEdge[at].index
        }
        if (at == to) compress(cycle)
    }

    private fun compress(cycle: BooleanArray) {
        val (onCycleIds, offCycleIds) = cycle.indices.partition { cycle[it] }
        assert(onCycleIds.size >= 2)
        val leader = onCycleIds[0]
        for (i in onCycleIds) for (j in onCycleIds) distances[i][j] = Long.MAX_VALUE
        for (i in offCycleIds) {
            var minOutgoing = Long.MAX_VALUE
            var minIncoming = Long.MAX_VALUE
            for (j in onCycleIds) {
                minOutgoing = min(minOutgoing, distances[i][j])
                minIncoming = min(minIncoming, strategy.split(distances[j][i], incomingEdge[j].value))
                distances[i][j] = Long.MAX_VALUE
                distances[j][i] = Long.MAX_VALUE
            }
            distances[i][leader] = minOutgoing
            distances[leader][i] = minIncoming
        }
        incomingEdge[leader] = IndexedValue(leader, 0L)
        addEdge(leader)
    }

    fun solve() {
        savedTo = 0
        savedEdge = getMinimumIncomingEdge(0)
        for (i in 1 until vertexSize) addEdge(i)
    }
}
