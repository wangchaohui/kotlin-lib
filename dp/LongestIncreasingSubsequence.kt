/** The lower bound of [low, high], which `predicate.test(it)` is true. */
fun lowerBound(low: Int, high: Int, predicate: IntPredicate): Int? {
    if (low > high) return null
    var l = low
    var h = high
    while (l < h) {
        val m = (l + h) / 2
        if (predicate.test(m)) {
            h = m
        } else {
            l = m + 1
        }
    }
    return l.takeIf(predicate::test)
}

class LongestIncreasingSubsequence(list: List<Int>) {
    val n = list.size
    val lAns = IntArray(n)
    val l = mutableListOf<Int>()
    val rAns = IntArray(n)
    val r = mutableListOf<Int>()

    init {
        for (i in list.indices) {
            lAns[i] = if (l.isEmpty() || list[i] > l.last()) {
                l += list[i]
                l.size
            } else {
                val x = lowerBound(0, l.size - 1) { l[it] >= list[i] }!!
                l[x] = list[i]
                x + 1
            }
        }

        for (i in list.indices.reversed()) {
            rAns[i] = if (r.isEmpty() || list[i] < r.last()) {
                r += list[i]
                r.size
            } else {
                val x = lowerBound(0, r.size - 1) { r[it] <= list[i] }!!
                r[x] = list[i]
                x + 1
            }
        }
    }
}
