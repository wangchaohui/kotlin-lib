class RangeSum2D(values: List<List<Int>>) {
    private val n = values.size
    private val m = values[0].size
    private val sum = Array(n) { LongArray(m) }
    private fun sum(i: Int, j: Int) = sum.getOrNull(i)?.getOrNull(j) ?: 0

    fun rangeSum(bottom: Int, right: Int): Long {
        check(bottom in 0..n && right in 0..m)
        return sum(bottom - 1, right - 1)
    }

    /** Queries the sum of the range. */
    fun rangeSum(top: Int, left: Int, bottom: Int, right: Int): Long {
        check(top in 0..bottom && bottom <= n)
        check(left in 0..right && right <= m)
        return rangeSum(bottom, right) - rangeSum(top, right) - rangeSum(bottom, left) + rangeSum(top, left)
    }

    fun rangeSumTile(bottom: Int, right: Int): Long {
        check(bottom >= 0 && right >= 0)
        if (bottom <= n && right <= m) return rangeSum(bottom, right)
        val a = bottom / n
        val i = bottom % n
        val b = right / m
        val j = right % m
        return rangeSum(n, m) * a * b + rangeSum(i, m) * b + rangeSum(n, j) * a + rangeSum(i, j)
    }

    fun rangeSumTile(top: Int, left: Int, bottom: Int, right: Int): Long {
        check(top in 0..bottom && left in 0..right)
        return rangeSumTile(bottom, right) -
                rangeSumTile(top, right) -
                rangeSumTile(bottom, left) +
                rangeSumTile(top, left)
    }

    init {
        for (i in 0 until n) for (j in 0 until m) {
            sum[i][j] = sum(i - 1, j) + sum(i, j - 1) - sum(i - 1, j - 1) + values[i][j]
        }
    }
}
