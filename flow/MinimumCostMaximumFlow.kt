class MinimumCostMaximumFlow(vertexSize: Int, private val s: Int, private val t: Int) {
    data class Edge(
        val u: Int,
        val v: Int,
        var capacity: Int,
        var cost: Int,
        var flow: Int = 0,
    ) {
        lateinit var reverse: Edge
        val remaining: Int
            get() = capacity - flow
    }

    private val costs = IntArray(vertexSize)
    private val edges = Array(vertexSize) { mutableListOf<Edge>() }
    private val currentEdges = IntArray(vertexSize)
    private val visited = BooleanArray(vertexSize)

    fun addEdge(u: Int, v: Int, capacity: Int, cost: Int, reversedCapacity: Int = 0): Edge {
        val edge = Edge(u, v, capacity, cost).also { edges[u] += it }
        edge.reverse = Edge(v, u, reversedCapacity, -cost).apply { reverse = edge }.also { edges[v] += it }
        return edge
    }

    fun dinic(): Pair<Long, Long> {
        var f = 0L
        var c = 0L
        while (spfa()) {
            currentEdges.fill(0)
            val (flow, cost) = dfs(s, Int.MAX_VALUE)
            f += flow
            c += cost
        }
        return f to c
    }

    private fun spfa(): Boolean {
        costs.fill(Int.MAX_VALUE)
        costs[s] = 0
        val q = ArrayDeque<Int>()
        q.add(s)
        visited[s] = true
        while (q.isNotEmpty()) {
            val u = q.removeFirst()
            visited[u] = false
            for (edge in edges[u]) {
                val v = edge.v
                if (edge.remaining > 0 && costs[v] > costs[u] + edge.cost) {
                    costs[v] = costs[u] + edge.cost
                    if (!visited[v]) {
                        q.add(v)
                        visited[v] = true
                    }
                }
            }
        }
        return costs[t] < Int.MAX_VALUE
    }

    private fun dfs(u: Int, flowLimit: Int): Pair<Int, Int> {
        if (u == t || flowLimit == 0) return flowLimit to 0
        visited[u] = true
        var pushedFlow = 0
        var pushedCost = 0
        while (pushedFlow < flowLimit) {
            val edge = edges[u].getOrNull(currentEdges[u]++) ?: break
            val v = edge.v
            if (visited[v] || edge.remaining == 0 || costs[v] != costs[u] + edge.cost) continue
            val (pathFlow, pathCost) = dfs(v, min(flowLimit - pushedFlow, edge.remaining))
            pushedFlow += pathFlow
            pushedCost += pathCost + pathFlow * edge.cost
            edge.flow += pathFlow
            edge.reverse.flow -= pathFlow
        }
        visited[u] = false
        return pushedFlow to pushedCost
    }
}
