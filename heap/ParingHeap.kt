class PairingHeap<T>(private val comparator: Comparator<T>) {
    private data class Node<T>(
        val element: T,
        var firstChild: Node<T>? = null,
        var nextSibling: Node<T>? = null,
    ) {
        fun addChild(child: Node<T>) {
            check(child.nextSibling == null)
            child.nextSibling = firstChild
            firstChild = child
        }
    }

    private var root: Node<T>? = null

    fun add(element: T) {
        root = meld(Node(element), root)
    }

    fun peek(): T? = root?.element

    fun pop() {
        root = mergePairs(root!!.firstChild)
    }

    fun poll(): T {
        val top = peek()
        pop()
        return top!!
    }

    fun merge(other: PairingHeap<T>) {
        root = meld(root, other.root)
        other.root = null
    }

    private fun meld(x: Node<T>?, y: Node<T>?): Node<T>? = when {
        x == null -> y
        y == null -> x
        comparator.compare(x.element, y.element) < 0 -> x.apply { addChild(y) }
        else -> y.apply { addChild(x) }
    }

    private fun mergePairs(firstChild: Node<T>?): Node<T>? {
        var x = firstChild
        val pairs = mutableListOf<Node<T>?>()
        while (x != null) {
            val y = x.nextSibling
            if (y == null) {
                pairs.add(x)
                break
            }
            val z = y.nextSibling
            x.nextSibling = null
            y.nextSibling = null
            pairs.add(meld(x, y))
            x = z
        }
        return pairs.reduceRightOrNull(::meld)
    }
}
