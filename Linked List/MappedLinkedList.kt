class MappedLinkedList<T> {
    private var root = Node().apply {
        prev = this
        next = this
    }

    val map = mutableMapOf<T, Node>()

    inner class Node(
        val value: T? = null,
        var prev: Node? = null,
        var next: Node? = null,
    ) {
        init {
            next?.prev = this
            prev?.next = this
        }

        fun addNext(value: T) {
            map[value] = Node(value, this, next)
        }

        fun remove() {
            next?.prev = prev
            prev?.next = next
            map.remove(value)
        }
    }

    fun addLast(value: T) {
        root.prev!!.addNext(value)
    }

    fun toList() = buildList<T> {
        var node = root.next
        while (node != root) {
            add(node!!.value!!)
            node = node.next
        }
    }
}
