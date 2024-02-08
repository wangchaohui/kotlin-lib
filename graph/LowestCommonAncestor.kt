class SparseTable<T>(list: List<T>, private val merge: (T, T) -> T) {
    private val n = list.size
    private val logN = n.takeHighestOneBit().countTrailingZeroBits()
    private val table = mutableListOf(list.toList()).apply {
        for (i in 1..logN) {
            val lastRangeSize = 1 shl i - 1
            add(List(n - lastRangeSize * 2 + 1) { j ->
                merge(this[i - 1][j], this[i - 1][j + lastRangeSize])
            })
        }
    }

    private val logs = IntArray(n).apply {
        for (i in 2..<size) this[i] = this[i / 2] + 1
    }

    /** Query for range [l, r] */
    fun query(l: Int, r: Int): T {
        val log = logs[r - l]
        return merge(table[log][l], table[log][r - (1 shl log) + 1])
    }
}

data class Graph(val maxVertexId: Int) {
    private val adjacencyList = Array(maxVertexId + 1) { mutableListOf<Int>() }

    fun addEdge(u: Int, v: Int) {
        adjacencyList[u] += v
        adjacencyList[v] += u
    }

    class VertexEulerTour(
        val firstVisitedPosition: IntArray,
        val vertexEulerTour: List<Pair<Int, Int>>, // depth to vertexId
    )

    fun buildVertexEulerTour(start: Int): VertexEulerTour {
        val firstVisitedPosition = IntArray(maxVertexId + 1) { -1 }
        val vertexEulerTour = mutableListOf<Pair<Int, Int>>()
        fun dfs(u: Int, depth: Int) {
            firstVisitedPosition[u] = vertexEulerTour.size
            vertexEulerTour += depth to u
            for (v in adjacencyList[u]) {
                if (firstVisitedPosition[v] == -1) {
                    dfs(v, depth + 1)
                    vertexEulerTour += depth to u
                }
            }
        }
        dfs(start, 0)
        return VertexEulerTour(firstVisitedPosition, vertexEulerTour)
    }
}

class LowestCommonAncestor(graph: Graph, root: Int) {
    private val vertexEulerTour = graph.buildVertexEulerTour(root)
    private val sparseTable = SparseTable(vertexEulerTour.vertexEulerTour) { a, b ->
        if (a.first < b.first) a else b
    }

    fun lca(u: Int, v: Int): Int {
        val uPos = vertexEulerTour.firstVisitedPosition[u]
        val vPos = vertexEulerTour.firstVisitedPosition[v]
        return sparseTable.query(min(uPos, vPos), max(uPos, vPos)).second
    }
}
