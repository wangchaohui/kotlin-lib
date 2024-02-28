class DigitDp<T>(
    private val emptyFactory: () -> T,
    private val initialize: (T) -> Unit,
    private val transfer: (pre: T, now: T, index: Int, digit: Int) -> Unit,
) {
    fun solve(n: Long): Pair<T, T> {
        var f1 = emptyFactory()
        var f2 = emptyFactory()
        initialize(f1)
        for (i in digitCount(n) downTo 0) {
            val g1 = emptyFactory()
            val g2 = emptyFactory()
            val d = 10.pow(i)
            for (j in 0..9) {
                val dj = d * j
                if (dj > n) break
                val dn = (n / d).mod(10)
                when {
                    j < dn -> transfer(f1, g2, i, j)
                    j == dn -> transfer(f1, g1, i, j)
                }
                transfer(f2, g2, i, j)
            }
            f1 = g1
            f2 = g2
        }
        return f1 to f2
    }

    private fun digitCount(n: Long): Int {
        var d = 0
        var m = n
        while (m >= 10) {
            m /= 10
            d++
        }
        return d
    }

    companion object {
        fun Int.pow(exponent: Int): Long {
            var d = 1L
            repeat(exponent) { d *= this }
            return d
        }
    }
}

fun calculate(n: Long, sum: Int): Long {
    val digitDp = DigitDp(
        emptyFactory = { Array(sum + 1) { LongArray(sum) } },
        initialize = { it[0][0] = 1L },
        transfer = { pre, now, i, j ->
            val ij = 10.pow(i) * j
            for (x in 0..sum - j) {
                for (y in 0 until sum) {
                    now[x + j][(y + ij).mod(sum)] += pre[x][y]
                }
            }
        },
    )
    val (f1, f2) = digitDp.solve(n)
    return f1[sum][0] + f2[sum][0]
}
