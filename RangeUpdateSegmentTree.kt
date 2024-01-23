class RangeUpdateSegmentTree(private val n: Int) {
    data class Interval(
        var b: Int = 0,
        var v: Int = 1000000,
    )

    private val t = Array(n * 2) { Interval() }

    private fun set(p: Int, b: Int, v: Int) {
        if (p !in t.indices || t[p].v < v) return
        t[p].b = b
        t[p].v = v
    }

    private fun push(p: Int) {
        var s = p.takeHighestOneBit()
        while (s > 0) {
            val i = p / s
            if (t[i].b > 0) {
                set(i * 2, t[i].b, t[i].v)
                set(i * 2 + 1, t[i].b, t[i].v)
                // clear lazy tag
            }
            s /= 2
        }
    }

    fun query(p: Int): Interval {
        val i = p + n
        push(i)
        return t[i]
    }

    fun update(l: Int, r: Int, b: Int, v: Int) {
        var i = l + n
        var j = r + n
        while (i < j) {
            if (i % 2 == 1) set(i++, b, v)
            if (j % 2 == 1) set(--j, b, v)
            i /= 2
            j /= 2
        }
        // updates the parents of l & r
    }

    data class D(
        val key: String,
        val interval: Interval,
    ) {
        override fun toString() = if (key.isNotEmpty()) "$key -> $interval" else ""
    }

    fun debug(): List<D> {
        val l = Array(t.size) { 0 }
        val r = Array(t.size) { -1 }
        for (i in 0..<n) {
            l[i + n] = i
            r[i + n] = i
        }
        for (i in t.lastIndex downTo 2) {
            if (i % 2 == 0) {
                l[i / 2] = l[i]
            } else {
                r[i / 2] = r[i]
            }
        }
        return t.mapIndexed { i, interval ->
            D(
                when {
                    l[i] > r[i] -> ""
                    l[i] == r[i] -> "[${l[i]}]"
                    else -> "[${l[i]},${r[i]}]"
                },
                interval,
            )
        }
    }
}
