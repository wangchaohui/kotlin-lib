fun mergeSort(a: List<Int>): List<Int> {
    if (a.size <= 1) return a
    val x = mergeSort(a.subList(0, a.size / 2))
    val y = mergeSort(a.subList(a.size / 2, a.size))
    return buildList {
        var i = 0
        var j = 0
        while (i < x.size || j < y.size) {
            if (i < x.size && (j == y.size || compare(x[i], y[j]))) add(x[i++])
            else add(y[j++])
        }
    }
}
