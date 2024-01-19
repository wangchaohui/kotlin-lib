class BinaryHeap<T : BinaryHeap.Item>(private val comparator: Comparator<T>) {

    interface Item {
        var pos: Int
    }

    private val a = mutableListOf<T>()

    fun add(item: T) {
        item.pos = a.size
        a.add(item)
        popUp(item.pos)
    }

    fun peek(): T? = a.firstOrNull()

    fun pop(): T? {
        val top = a.firstOrNull()
        val last = a.removeLast()
        if (a.isNotEmpty()) {
            a[0] = last.apply { pos = 0 }
            heapify(0)
        }
        return top?.apply { pos = -1 }
    }

    fun modify(item: T) {
        if (item.pos in a.indices) heapify(popUp(item.pos))
    }

    private fun exchange(i: Int, j: Int) {
        val ai = a[i]
        a[i] = a[j].apply { pos = i }
        a[j] = ai.apply { pos = j }
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
