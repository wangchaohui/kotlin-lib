class RangeAllSame<T>(list: List<T>) {
    val firstDifferentIndex = IntArray(list.size)

    init {
        list.forEachIndexed { i, a ->
            firstDifferentIndex[i] =
                if (i > 0 && a == list[i - 1]) firstDifferentIndex[i - 1] else i - 1
        }
    }

    /** All list elements in the range [l, r] are same. */
    fun rangeAllSame(l: Int, r: Int): Boolean = firstDifferentIndex[r] < l
}
