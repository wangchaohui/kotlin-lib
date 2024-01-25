class DisjointSet(size: Int) {
    private data class Node(
        var id: Int,
    ) {
        var parent: Node = this
        var size: Int = 1
    }

    private val set = Array(size, ::Node)

    fun find(i: Int): Int = findNode(i).id

    private fun findNode(i: Int): Node {
        var x = set[i]
        while (x.parent != x) {
            x.parent = x.parent.parent
            x = x.parent
        }
        return x
    }

    fun union(i: Int, j: Int) {
        var x = findNode(i)
        var y = findNode(j)
        if (x == y) return
        if (x.size < y.size) x = y.also { y = x }
        y.parent = x
        x.size += y.size
    }
}

class MinimumSpanningTree(private val vertexSize: Int, edges: List<Edge>) {
    data class Edge(
        val u: Int,
        val v: Int,
        val w: Int,
    )

    private val set = DisjointSet(vertexSize)
    private val edges = edges.sortedBy { it.w }

    fun kruskal(): List<Edge> = buildList {
        for (e in edges) {
            if (set.find(e.u) != set.find(e.v)) {
                add(e)
                set.union(e.u, e.v)
            }
        }
    }
}
