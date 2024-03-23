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

/** The lower bound of [low, high], which `predicate.test(it)` is true. */
fun lowerBound(low: Long, high: Long, predicate: LongPredicate): Long? {
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
fun upperBound(low: Long, high: Long, predicate: LongPredicate): Long? {
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

/** The upper bound of [low, high], which `predicate.test(it)` is true. */
fun upperBound(low: Double, high: Double, predicate: DoublePredicate): Double? {
    if (low > high) return null
    var l = low
    var h = high
    while (l + 1e-9 < h) {
        val m = (l + h) / 2
        if (l == m) break
        if (predicate.test(m)) {
            l = m
        } else {
            if (h == m) break
            h = m
        }
    }
    return l.takeIf(predicate::test)
}
