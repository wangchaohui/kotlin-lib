class RangeSum2D(values: List<List<Int>>) {
    private val n = values.size
    private val m = values[0].size

    private val sum = Array(n) { LongArray(m) }

    private fun sum(i: Int, j: Int) = sum.getOrNull(i)?.getOrNull(j) ?: 0

    /** Queries the sum of the range. */
    fun rangeSum(top: Int, left: Int, bottom: Int, right: Int): Long {
        check(top in 0..bottom && bottom <= n)
        check(left in 0..right && right <= m)
        return sum(bottom - 1, right - 1) -
                sum(top - 1, right - 1) -
                sum(bottom - 1, left - 1) +
                sum(top - 1, left - 1)
    }

    init {
        for (i in 0 until n) for (j in 0 until m) {
            sum[i][j] = sum(i - 1, j) + sum(i, j - 1) - sum(i - 1, j - 1) + values[i][j]
        }
    }
}
