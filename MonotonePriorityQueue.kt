class MonotonePriorityQueue<T>(private val comparator: Comparator<T>) {
    private val q = ArrayDeque<T>()

    fun add(value: T) {
        while (q.isNotEmpty() && comparator.compare(q.last(), value) >= 0) q.removeLast()
        q.add(value)
    }

    fun first(predicate: (T) -> Boolean): T {
        while (!predicate(q.first())) q.removeFirst()
        return q.first()
    }

    fun firstOrNull(predicate: (T) -> Boolean): T? {
        while (q.isNotEmpty() && !predicate(q.first())) q.removeFirst()
        return q.firstOrNull()
    }
}
