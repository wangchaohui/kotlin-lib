class LeftistTree<T>(private val comparator: Comparator<T>) {

    private data class Node<T>(
        val value: T,
        var s: Int = 1,
        var left: Node<T>? = null,
        var right: Node<T>? = null,
    )

    private var root: Node<T>? = null

    fun isEmpty(): Boolean = root == null

    fun clear() {
        root = null
    }

    fun add(value: T) {
        root = merge(Node(value), root)
    }

    fun findMin(): T? = root?.value

    fun deleteMin(): T? = root?.let { r ->
        r.value.also { root = merge(r.left, r.right) }
    }

    fun merge(other: LeftistTree<T>) {
        root = merge(root, other.root)
        other.root = null
    }

    private fun merge(x: Node<T>?, y: Node<T>?): Node<T>? {
        if (x == null) return y
        if (y == null) return x

        // Ensure x's value is smaller (or equal) for min-heap property
        if (comparator.compare(x.value, y.value) > 0) return merge(y, x)

        return x.apply {
            // Merge right subtrees, maintaining leftist property
            right = merge(right, y)

            // Update s (null path length) if right subtree is taller
            if ((left?.s ?: 0) < (right?.s ?: 0)) {
                left = right.also { right = left }
            }

            s = (right?.s ?: 0) + 1
        }
    }
}
