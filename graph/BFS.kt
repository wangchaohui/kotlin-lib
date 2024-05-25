class Graph(val maxVertexId: Int) {
    val adjacencyList = Array(maxVertexId + 1) { mutableListOf<Int>() }

    fun addEdge(u: Int, v: Int) {
        adjacencyList[u] += v
        adjacencyList[v] += u
    }
}

fun Graph.bfs(start: Int): IntArray {
    val distances = IntArray(maxVertexId + 1) { -1 }
    distances[start] = 0
    val queue = ArrayDeque(listOf(start))
    while (queue.isNotEmpty()) {
        val u = queue.removeFirst()
        for (v in adjacencyList[u]) {
            if (distances[v] == -1) {
                distances[v] = distances[u] + 1
                queue += v
            }
        }
    }
    return distances
}
