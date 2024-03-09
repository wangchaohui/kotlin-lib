class Tree(val maxVertexId: Int) {
    private val adjacencyList = Array(maxVertexId + 1) { mutableListOf<Int>() }

    fun addEdge(u: Int, v: Int) {
        adjacencyList[u] += v
        adjacencyList[v] += u
    }

    data class Node(val x: Int, val depth: Int)

    fun <T> dfsIterative(
        start: Int,
        initial: Node.() -> T,
        merge: (T, T) -> T,
        post: Node.(T) -> T = { it },
    ): T {
        val depth = IntArray(maxVertexId + 1) { -1 }
        depth[start] = 0
        val xStack = ArrayDeque(listOf(start to 0))
        val tStack = ArrayDeque<T>()
        while (xStack.isNotEmpty()) {
            val (x, edgeId) = xStack.removeLast()
            val node = Node(x, depth[x])
            tStack += if (edgeId == 0) {
                node.initial()
            } else {
                merge(tStack.removeLast(), tStack.removeLast())
            }
            var i = edgeId
            while (i in adjacencyList[x].indices) {
                val y = adjacencyList[x][i]
                if (depth[y] == -1) {
                    depth[y] = depth[x] + 1
                    xStack += x to i + 1
                    xStack += y to 0
                    break
                }
                i++
            }
            if (i == adjacencyList[x].size) tStack += node.post(tStack.removeLast())
        }
        return tStack.single()
    }

    fun <T> dfsRecursion(
        start: Int,
        initial: Node.() -> T,
        merge: (T, T) -> T,
        post: Node.(T) -> T = { it },
    ): T {
        val depth = IntArray(maxVertexId + 1) { -1 }
        depth[start] = 0
        fun dfs(x: Int): T {
            val node = Node(x, depth[x])
            var t = node.initial()
            for (y in adjacencyList[x]) if (depth[y] == -1) {
                depth[y] = depth[x] + 1
                t = merge(t, dfs(y))
            }
            return node.post(t)
        }
        return dfs(start)
    }
}
