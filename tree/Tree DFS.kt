data class Tree(val maxVertexId: Int) {
    private val adjacencyList = Array(maxVertexId + 1) { mutableListOf<Int>() }

    fun addEdge(parent: Int, child: Int) {
        adjacencyList[parent] += child
    }

    fun dfs(
        start: Int,
        preOrderTraversal: (x: Int) -> Unit = {},
        inOrderTraversal: (x: Int, visitedChildCount: Int) -> Unit = { _, _ -> },
        postOrderTraversal: (x: Int) -> Unit = {},
    ) {
        val stack = ArrayDeque(listOf(start to 0))
        while (stack.isNotEmpty()) {
            val (x, edgeId) = stack.removeLast()
            if (edgeId == 0) {
                preOrderTraversal(x)
            } else {
                inOrderTraversal(x, edgeId)
            }
            val y = adjacencyList[x].getOrNull(edgeId)
            if (y != null) {
                stack += x to edgeId + 1
                stack += y to 0
            } else {
                postOrderTraversal(x)
            }
        }
    }
}
