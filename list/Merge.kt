fun <T> merge(a: List<T>, b: List<T>, limit: Int, comparator: Comparator<T>, combine: (T, T) -> T): List<T> {
    var aIndex = 0
    var bIndex = 0
    val list = mutableListOf<T>()
    while (list.size < limit) {
        val aMax = a.getOrNull(aIndex)
        val bMax = b.getOrNull(bIndex)
        if (aMax == null) {
            if (bMax == null) break
            list += bMax
            bIndex++
            continue
        }
        if (bMax == null) {
            list += aMax
            aIndex++
            continue
        }
        val compare = comparator.compare(aMax, bMax)
        if (compare < 0) {
            list += aMax
            aIndex++
        } else if (compare > 0) {
            list += bMax
            bIndex++
        } else {
            list += combine(aMax, bMax)
            aIndex++
            bIndex++
        }
    }
    return list
}

val maximumAndCount = merge(listOf(1 to 1), listOf(2 to 1), 2, compareByDescending { it.first }) { x, y ->
    x.first to x.second + y.second
}
