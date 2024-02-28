class SegmentTree<T, M>(
    private val n: Int,
    private val identity: T,
    private val combine: (T, T) -> T,
    private val identityModification: M,
    private val combineModification: (M, M) -> M,
    private val modify: M.(T, size: Int) -> T,
) {
    private val h = 32 - Integer.numberOfLeadingZeros(n)
    private val tree = MutableList(2 * n) { identity }
    private val lazy = MutableList(n) { identityModification }
    private val size = MutableList(n) { 0 }

    constructor(
        values: List<T>,
        identity: T,
        combine: (T, T) -> T,
        identityModification: M,
        combineModification: (M, M) -> M,
        modify: M.(T, size: Int) -> T,
    ) : this(values.size, identity, combine, identityModification, combineModification, modify) {
        values.forEachIndexed { i, v -> tree[i + n] = v }
        for (i in n - 1 downTo 1) {
            tree[i] = combine(tree[i * 2], tree[i * 2 + 1])
            val sizeLeft = size.getOrElse(i * 2) { 1 }
            val sizeRight = size.getOrElse(i * 2 + 1) { 1 }
            size[i] = if (sizeLeft == sizeRight) sizeLeft + sizeRight else 0
        }
    }

    override fun toString(): String {
        val l = MutableList(2 * n) { it - n }
        for (i in n - 1 downTo 1) {
            if (size[i] > 0) l[i] = l[i * 2]
        }
        return l.withIndex().filter { it.value >= 0 }.joinToString(System.lineSeparator()) { (i, l) ->
            if (i >= n) "$l -> ${tree[i]}"
            else "[$l,${l + size[i]}) -> ${tree[i]} ${lazy[i]}"
        }
    }

    private fun M.modify(index: Int) {
        tree[index] = modify(tree[index], size.getOrElse(index) { 1 })
    }

    private fun M.apply(index: Int) {
        modify(index)
        if (index < n) lazy[index] = combineModification(lazy[index], this)
    }

    private fun build(index: Int) {
        var p = index
        while (p > 1) {
            p /= 2
            tree[p] = combine(tree[p * 2], tree[p * 2 + 1])
            lazy[p].modify(p)
        }
    }

    private fun push(p: Int) {
        for (s in h downTo 1) {
            val i = p shr s
            if (lazy[i] != identityModification) {
                lazy[i].apply(i * 2)
                lazy[i].apply(i * 2 + 1)
                lazy[i] = identityModification
            }
        }
    }

    /** Queries for the range `[l, r)`. */
    fun query(l: Int, r: Int): T {
        var resL = identity
        var resR = identity
        var i = l + n
        var j = r + n
        push(i)
        push(j - 1)
        while (i < j) {
            if (i and 1 > 0) resL = combine(resL, tree[i++])
            if (j and 1 > 0) resR = combine(tree[--j], resR)
            i /= 2
            j /= 2
        }
        return combine(resL, resR)
    }

    /** Updates for the range `[l, r)`. */
    fun update(l: Int, r: Int, modification: M) {
        var i = l + n
        var j = r + n
        while (i < j) {
            if (i and 1 > 0) modification.apply(i++)
            if (j and 1 > 0) modification.apply(--j)
            i /= 2
            j /= 2
        }
        build(l + n)
        build(r + n - 1)
    }

    fun update(index: Int, modification: M) {
        update(index, index + 1, modification)
    }
}

val segmentTree = SegmentTree<Long, Long>(
    values = emptyList(),
    identity = 0,
    combine = { x, y -> x + y },
    identityModification = 0,
    combineModification = { x, y -> x + y },
    modify = { sum, size -> sum + this * size },
)

