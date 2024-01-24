class SplitMergeTreap {
    data class Node(
        val value: Int,
        var l: Node? = null,
        var r: Node? = null,
        var count: Int = 1,
    ) {
        val priority = Random.nextInt()
        var size: Int = count
        fun updateSize() {
            size = count + l.size + r.size
        }
    }

    private var root: Node? = null

    fun add(value: Int) {
        val (l, r) = root.split(value)
        var (ll, lr) = l.split(value - 1)
        if (lr == null) {
            lr = Node(value)
        } else {
            lr.count++
            lr.updateSize()
        }
        root = merge(merge(ll, lr), r)
    }

    val size: Int
        get() = root.size

    fun rank(value: Int): Int {
        val (l, r) = root.split(value - 1)
        return l.size.also { root = merge(l, r) }
    }

    companion object {
        val Node?.size: Int
            get() = this?.size ?: 0

        fun Node?.split(key: Int): Pair<Node?, Node?> = when {
            this == null -> null to null
            value <= key -> {
                val (rl, rr) = r.split(key)
                r = rl
                updateSize()
                this to rr
            }

            else -> {
                val (ll, lr) = l.split(key)
                l = lr
                updateSize()
                ll to this
            }
        }

        fun merge(u: Node?, v: Node?): Node? = when {
            u == null -> v
            v == null -> u
            u.priority < v.priority -> u.apply {
                r = merge(r, v)
                updateSize()
            }

            else -> v.apply {
                l = merge(u, l)
                updateSize()
            }
        }
    }
}
