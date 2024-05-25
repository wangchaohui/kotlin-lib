class Graph(val maxVertexId: Int) {
    val adjacencyList = Array(maxVertexId + 1) { mutableListOf<Int>() }

    fun addEdge(u: Int, v: Int) {
        adjacencyList[u] += v
        adjacencyList[v] += u
    }
}

fun Graph.dfsDistanceAndParent(start: Int): Pair<IntArray, IntArray> {
    val distance = IntArray(maxVertexId + 1) { -1 }
    val parent = IntArray(maxVertexId + 1) { -1 }
    distance[start] = 0
    fun dfs(u: Int) {
        for (v in adjacencyList[u]) if (distance[v] == -1) {
            distance[v] = distance[u] + 1
            parent[v] = u
            dfs(v)
        }
    }
    dfs(start)
    return distance to parent
}
