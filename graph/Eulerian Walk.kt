data class Edge(
    val u: Int,
    val v: Int,
)

/**
 * Edges could be sorted, result is lexicographical ordered based on edges.
 */
class UndirectedGraph(val maxVertexId: Int, val edges: List<Edge>) {
    data class InternalEdge(
        val v: Int,
        val edgeIdInV: Int,
    )

    val adjacencyList = Array(maxVertexId + 1) { mutableListOf<InternalEdge>() }

    fun addEdge(u: Int, v: Int) {
        adjacencyList[u] += InternalEdge(v, adjacencyList[v].size)
        if (u != v) adjacencyList[v] += InternalEdge(u, adjacencyList[u].lastIndex)
    }

    init {
        for ((u, v) in edges) addEdge(u, v)
    }
}

fun UndirectedGraph.hierholzer(start: Int): List<Int> {
    val nextEdgeId = IntArray(maxVertexId + 1) { 0 }
    val visited = Array(maxVertexId + 1) { BitSet() }
    val stack = ArrayDeque<Int>()
    fun dfs(u: Int) {
        while (nextEdgeId[u] < adjacencyList[u].size) {
            val id = nextEdgeId[u]++
            if (!visited[u][id]) {
                val (v, edgeIdInV) = adjacencyList[u][id]
                visited[u][id] = true
                visited[v][edgeIdInV] = true
                dfs(v)
            }
        }
        stack += u
    }
    dfs(start)
    return stack.reversed()
}

/**
 * Finds the Eulerian Walk, only works when Eulerian Walk exists.
 */
fun UndirectedGraph.eulerianWalk(): List<Int> {
    val degree = IntArray(maxVertexId + 1)
    for ((u, v) in edges) if (u != v) {
        degree[u]++
        degree[v]++
    }
    var start = 0
    for (i in 1..maxVertexId) {
        if (degree[start] == 0 && degree[i] > 0 || degree[start] % 2 == 0 && degree[i] % 2 == 1) {
            start = i
        }
    }
    return hierholzer(start)
}
