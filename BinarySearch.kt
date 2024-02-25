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

/** The upper bound of [low, high], which `predicate.test(it)` is true. */
fun upperBound(low: Int, high: Int, predicate: IntPredicate): Int? {
    if (low > high) return null
    var l = low
    var h = high
    while (l < h) {
        val m = (l + h + 1) / 2
        if (predicate.test(m)) {
            l = m
        } else {
            h = m - 1
        }
    }
    return l.takeIf(predicate::test)
}
