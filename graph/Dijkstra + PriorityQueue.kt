class Graph<DistanceType, EdgeType>(
    val maxVertexId: Int,
    val distanceComparator: Comparator<DistanceType>,
    val calculateDistanceV: (distanceU: DistanceType, edge: EdgeType) -> DistanceType?,
) {
    private val adjacencyList = Array(maxVertexId + 1) { mutableListOf<Pair<Int, EdgeType>>() }

    fun addEdge(u: Int, v: Int, edge: EdgeType) {
        adjacencyList[u] += v to edge
    }

    fun dijkstra(start: Int, distanceStart: DistanceType): List<DistanceType?> {
        val distances = MutableList<DistanceType?>(maxVertexId + 1) { null }
        val q = PriorityQueue<Pair<Int, DistanceType>>(compareBy(distanceComparator) { it.second })
        q += start to distanceStart
        while (q.isNotEmpty()) {
            val (u, distanceU) = q.poll()
            if (distances[u] != null) continue
            distances[u] = distanceU
            for ((v, e) in adjacencyList[u]) {
                calculateDistanceV(distanceU, e)?.let { distanceV -> q += v to distanceV }
            }
        }
        return distances
    }
}

val graph = Graph<Long, Long>(
    maxVertexId = 10,
    distanceComparator = compareBy { it },
    calculateDistanceV = { distanceU, edge -> distanceU + edge },
)
