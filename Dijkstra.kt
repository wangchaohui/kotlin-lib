class BinaryHeap<T : BinaryHeap.Item>(private val comparator: Comparator<T>) {

    interface Item {
        var binaryHeapPos: Int
    }

    private val a = mutableListOf<T>()

    fun add(item: T) {
        item.binaryHeapPos = a.size
        a.add(item)
        popUp(item.binaryHeapPos)
    }

    fun peek(): T? = a.firstOrNull()

    fun pop(): T? {
        val top = a.firstOrNull()
        val last = a.removeLast()
        if (a.isNotEmpty()) {
            a[0] = last.apply { binaryHeapPos = 0 }
            heapify(0)
        }
        return top?.apply { binaryHeapPos = -1 }
    }

    fun modify(item: T) {
        if (item.binaryHeapPos in a.indices) heapify(popUp(item.binaryHeapPos))
    }

    val size get() = a.size

    private fun exchange(i: Int, j: Int) {
        val ai = a[i]
        a[i] = a[j].apply { binaryHeapPos = i }
        a[j] = ai.apply { binaryHeapPos = j }
    }

    private fun popUp(index: Int): Int {
        var i = index
        while (i > 0) {
            val pi = (i - 1) / 2
            if (comparator.compare(a[i], a[pi]) >= 0) break
            exchange(i, pi)
            i = pi
        }
        return i
    }

    private tailrec fun heapify(i: Int) {
        val left = 2 * i + 1
        val right = 2 * i + 2
        var smallest = i

        if (left < a.size && comparator.compare(a[left], a[smallest]) < 0) {
            smallest = left
        }
        if (right < a.size && comparator.compare(a[right], a[smallest]) < 0) {
            smallest = right
        }
        if (smallest != i) {
            exchange(i, smallest)
            heapify(smallest)
        }
    }
}

class Dijkstra<T : Dijkstra.Vertex>(
    private val vertices: Iterable<T>,
    private val edges: (T) -> Iterable<Edge<T>>,
) {
    interface Vertex : BinaryHeap.Item {
        var dist: Long
        var prev: Vertex?
    }

    data class Edge<T : Vertex>(
        val v: T,
        val w: Long,
    )

    fun dijkstra() {
        val q = BinaryHeap<T>(compareBy(Vertex::dist))
        vertices.forEach(q::add)
        while (q.size > 0) {
            val u = q.pop()!!
            if (u.dist == Long.MAX_VALUE) break
            for ((v, w) in edges(u).filter { it.v.binaryHeapPos >= 0 }) {
                val alt = u.dist + w
                if (alt < v.dist) {
                    v.dist = alt
                    v.prev = u
                    q.modify(v)
                }
            }
        }
    }
}
