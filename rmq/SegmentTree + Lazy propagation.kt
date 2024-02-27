class SegmentTree<T, M>(
    private val n: Int,
    private val identity: T,
    private val combine: (T, T) -> T,
    private val identityModification: M,
    private val combineModification: (M, M) -> M,
    private val modify: M.(T) -> T,
) {
    private val h = 32 - Integer.numberOfLeadingZeros(n)
    private val tree = MutableList(2 * n) { identity }
    private val lazy = MutableList(n) { identityModification }

    constructor(
        values: List<T>,
        identity: T,
        combine: (T, T) -> T,
        identityModification: M,
        combineModification: (M, M) -> M,
        modify: M.(T) -> T,
    ) : this(values.size, identity, combine, identityModification, combineModification, modify) {
        values.forEachIndexed { i, v -> tree[i + n] = v }
        for (i in n - 1 downTo 1) tree[i] = combine(tree[i * 2], tree[i * 2 + 1])
    }

    private fun apply(index: Int, modification: M) {
        tree[index] = modification.modify(tree[index])
        if (index < n) lazy[index] = combineModification(lazy[index], modification)
    }

    private fun build(index: Int) {
        var p = index
        while (p > 1) {
            p /= 2
            tree[p] = lazy[p].modify(combine(tree[p * 2], tree[p * 2 + 1]))
        }
    }

    private fun push(p: Int) {
        for (s in h downTo 1) {
            val i = p shr s
            if(lazy[i] != identityModification) {
                apply(i * 2, lazy[i])
                apply(i * 2 + 1, lazy[i])
                lazy[i] = identityModification
            }
        }
    }

    /** Queries for the range `[l, r)`. */
    fun query(l: Int, r: Int): T {
        var resL: T = identity
        var resR: T = identity
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
            if (i and 1 > 0) apply(i++, modification)
            if (j and 1 > 0) apply(--j, modification)
            i /= 2
            j /= 2
        }
        build(l)
        build(r - 1)
    }

    fun update(index: Int, modification: M) {
        update(index, index + 1, modification)
    }
}
