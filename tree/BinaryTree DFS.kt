class BinaryTree(maxVertexId: Int) {
    private val adjacencyList = Array(maxVertexId + 1) { IntArray(2) { -1 } }

    fun addLeftChild(parent: Int, child: Int) {
        adjacencyList[parent][0] = child
    }

    fun addRightChild(parent: Int, child: Int) {
        adjacencyList[parent][1] = child
    }

    fun dfs(
        start: Int,
        preOrderTraversal: (x: Int) -> Unit = {},
        inOrderTraversal: (x: Int) -> Unit = {},
        postOrderTraversal: (x: Int) -> Unit = {},
    ) {
        val stack = ArrayDeque(listOf(start to 0))
        while (stack.isNotEmpty()) {
            val (x, edgeId) = stack.removeLast()
            when (edgeId) {
                0 -> preOrderTraversal(x)
                1 -> inOrderTraversal(x)
                2 -> {
                    postOrderTraversal(x)
                    continue
                }
            }
            stack += x to edgeId + 1
            val y = adjacencyList[x][edgeId]
            if (y != -1) stack += y to 0
        }
    }
}
