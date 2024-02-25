class CumulativeLongSumFromLeft(size: Int) {
    private val sum = LongArray(size)

    /** Sum of [l, r] */
    fun rangeSum(l: Int, r: Int): Long =
        if (l <= r && r >= 0) sum[r.coerceAtMost(sum.lastIndex)] - sum.getOrElse(l - 1) { 0L }
        else 0L

    fun setFromLeft(index: Int, value: Long) {
        sum[index] = sum.getOrElse(index - 1) { 0L } + value
    }
}

class CumulativeLongSumFromRight(size: Int) {
    private val sum = LongArray(size)

    /** Sum of [l, r] */
    fun rangeSum(l: Int, r: Int): Long =
        if (l <= r && l <= sum.lastIndex) sum[l.coerceAtLeast(0)] - sum.getOrElse(r + 1) { 0L }
        else 0L

    fun setFromRight(index: Int, value: Long) {
        sum[index] = sum.getOrElse(index + 1) { 0L } + value
    }
}

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
