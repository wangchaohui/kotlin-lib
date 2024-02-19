class Graph(val maxVertexId: Int) {
    val adjacencyList = Array(maxVertexId + 1) { mutableListOf<Int>() }

    fun addEdge(u: Int, v: Int) {
        adjacencyList[u] += v
        adjacencyList[v] += u
    }
}

fun Graph.bfsReachableVertices(start: Int, distanceLimit: Int = Int.MAX_VALUE): Set<Int> {
    val distances = mutableSetOf(start)
    val queue = ArrayDeque(listOf(start to 0))
    while (queue.isNotEmpty()) {
        val (u, distanceU) = queue.removeFirst()
        if (distanceU == distanceLimit) break
        for (v in adjacencyList[u]) {
            if (distances.add(v)) {
                queue += v to distanceU + 1
            }
        }
    }
    return distances
}
