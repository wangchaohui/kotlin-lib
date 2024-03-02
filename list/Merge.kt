fun <T> merge(a: List<T>, b: List<T>, limit: Int, comparator: Comparator<T>, combine: (T, T) -> T): List<T> {
    var aIndex = 0
    var bIndex = 0
    val list = mutableListOf<T>()
    while (list.size < limit) {
        val aItem = a.getOrNull(aIndex)
        val bItem = b.getOrNull(bIndex)
        if (aItem == null) {
            if (bItem == null) break
            list += bItem
            bIndex++
            continue
        }
        if (bItem == null) {
            list += aItem
            aIndex++
            continue
        }
        val compare = comparator.compare(aItem, bItem)
        if (compare < 0) {
            list += aItem
            aIndex++
        } else if (compare > 0) {
            list += bItem
            bIndex++
        } else {
            list += combine(aItem, bItem)
            aIndex++
            bIndex++
        }
    }
    return list
}

val maximumAndCount = merge(listOf(1 to 1), listOf(2 to 1), 2, compareByDescending { it.first }) { x, y ->
    x.first to x.second + y.second
}
