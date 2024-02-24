class CumulativeDoubleSumFromLeft(size: Int) {
    private val sum = DoubleArray(size)

    /** Sum of [l, r] */
    fun rangeSum(l: Int, r: Int): Double =
        if (l <= r && r >= 0) sum[r.coerceAtMost(sum.lastIndex)] - sum.getOrElse(l - 1) { 0.0 }
        else 0.0

    fun setFromLeft(index: Int, value: Double) {
        sum[index] = sum.getOrElse(index - 1) { 0.0 } + value
    }
}

class CumulativeDoubleSumFromRight(size: Int) {
    private val sum = DoubleArray(size)

    /** Sum of [l, r] */
    fun rangeSum(l: Int, r: Int): Double =
        if (l <= r && l <= sum.lastIndex) sum[l.coerceAtLeast(0)] - sum.getOrElse(r + 1) { 0.0 }
        else 0.0

    fun setFromRight(index: Int, value: Double) {
        sum[index] = sum.getOrElse(index + 1) { 0.0 } + value
    }
}
