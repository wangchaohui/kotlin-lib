class MaximumFlow(vertexSize: Int, private val s: Int, private val t: Int) {
    data class Edge(
        val u: Int,
        val v: Int,
        var capacity: Int,
        var flow: Int = 0,
    ) {
        lateinit var reverse: Edge
    }

    private val level = IntArray(vertexSize)
    private val edges = Array(vertexSize) { mutableListOf<Edge>() }
    private val currentEdges = IntArray(vertexSize)

    fun addEdge(u: Int, v: Int, capacity: Int, reversedCapacity: Int = 0): Edge {
        val edge = Edge(u, v, capacity).also { edges[u] += it }
        edge.reverse = Edge(v, u, reversedCapacity).apply { reverse = edge }.also { edges[v] += it }
        return edge
    }

    fun dinic(): Long {
        var f = 0L
        while (bfs()) {
            currentEdges.fill(0)
            f += dfs(s, Int.MAX_VALUE)
        }
        return f
    }

    private fun bfs(): Boolean {
        level.fill(-1)
        level[s] = 0
        val q = ArrayDeque<Int>()
        q.add(s)
        while (q.isNotEmpty()) {
            val u = q.removeFirst()
            for (edge in edges[u]) {
                val v = edge.v
                if (level[v] == -1 && edge.capacity > edge.flow) {
                    level[v] = level[u] + 1
                    q.add(v)
                }
            }
        }
        return level[t] >= 0
    }

    private fun dfs(u: Int, flowLimit: Int): Int {
        if (u == t || flowLimit == 0) return flowLimit
        var pushedFlow = 0
        while (pushedFlow < flowLimit) {
            val edge = edges[u].getOrNull(currentEdges[u]++) ?: break
            val v = edge.v
            if (level[v] != level[u] + 1) continue
            val pathFlow = dfs(v, min(flowLimit - pushedFlow, edge.capacity - edge.flow))
            pushedFlow += pathFlow
            edge.flow += pathFlow
            edge.reverse.flow -= pathFlow
        }
        return pushedFlow
    }
}
