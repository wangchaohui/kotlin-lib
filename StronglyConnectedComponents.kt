class StronglyConnectedComponents(private val graph: List<List<Int>>) {
    data class Vertex(
        val id: Int,
        var index: Int? = null,
        var low: Int = -1,
        var onStack: Boolean = false,
    )

    fun tarjan(): List<List<Int>> {
        val vs = List(graph.size) { Vertex(it) }
        var index = 0
        val s = Stack<Vertex>()
        val sccList = mutableListOf<List<Int>>()

        fun strongConnect(v: Vertex) {
            v.index = index
            v.low = index
            index++
            s.push(v.apply { onStack = true })

            for (wId in graph[v.id]) {
                val w = vs[wId]
                val wIndex = w.index
                if (wIndex == null) {
                    strongConnect(w)
                    v.low = min(v.low, w.low)
                } else if (w.onStack) {
                    v.low = min(v.low, wIndex)
                }
            }

            if (v.low == v.index) {
                val scc = mutableListOf<Int>()
                do {
                    val w = s.pop().apply { onStack = false }
                    scc += w.id
                } while (w != v)
                sccList += scc
            }
        }

        for (v in vs) {
            if (v.index == null) strongConnect(v)
        }
        return sccList
    }
}
