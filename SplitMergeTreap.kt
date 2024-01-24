class SplitMergeTreap {
    private data class Node(
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

    fun del(value: Int) {
        val (l, r) = root.split(value)
        var (ll, lr) = l.split(value - 1)
        if (lr != null && lr.count > 1) {
            lr.count--
            lr.updateSize()
            ll = merge(ll, lr)
        }
        root = merge(ll, r)
    }

    val size: Int
        get() = root.size

    fun rank(value: Int): Int {
        val (l, r) = root.split(value - 1)
        return l.size.also { root = merge(l, r) }
    }

    operator fun get(rank: Int): Int {
        val (v, node) = root!!.getByRank(rank)
        root = node
        return v
    }

    fun previous(value: Int): Int {
        val (l, r) = root.split(value - 1)
        val (p, node) = l!!.getByRank(l.size - 1)
        root = merge(node, r)
        return p
    }

    fun next(value: Int): Int {
        val (l, r) = root.split(value)
        val (p, node) = r!!.getByRank(0)
        root = merge(l, node)
        return p
    }

    private companion object {
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

        fun Node?.splitByRank(rank: Int): Triple<Node?, Node?, Node?> = when {
            this == null -> Triple(null, null, null)
            rank < l.size -> {
                val (ll, lm, lr) = l.splitByRank(rank)
                l = lr
                updateSize()
                Triple(ll, lm, this)
            }

            rank < l.size + count -> Triple(l, this, r).also {
                l = null
                r = null
            }

            else -> {
                val (rl, rm, rr) = r.splitByRank(rank - l.size - count)
                r = rl
                updateSize()
                Triple(this, rm, rr)
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

        fun Node.getByRank(rank: Int): Pair<Int, Node> {
            val (l, m, r) = splitByRank(rank)
            return m!!.value to merge(merge(l, m), r)!!
        }
    }
}
