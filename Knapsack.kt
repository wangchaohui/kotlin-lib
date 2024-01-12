fun knapsack(target: Int, items: List<Int>) = BitSet(target + 1).apply {
    set(0)
    for (item in items) {
        shlOr(item)
    }
}

fun BitSet.shlOr(bitCount: Int) {
    if (bitCount > size()) return
    var p = previousSetBit(size() - bitCount)
    while (p >= 0) {
        set(p + bitCount)
        p = previousSetBit(p - 1)
    }
}

fun knapsackSplit(target: Int, items: List<Int>): List<BitSet> {
    val dp = List(target + 1) { BitSet(target + 1) }
    dp[0][0] = 1
    for (item in items) {
        for (i in target downTo 0) {
            dp.getOrNull(i + item)?.or(dp[i])
            dp[i].shlOr(item)
        }
    }
    return dp
}
