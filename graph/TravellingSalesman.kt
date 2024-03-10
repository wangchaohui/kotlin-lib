infix fun Int.hasBit(i: Int): Boolean = this and (1 shl i) > 0
infix fun Int.xorBit(i: Int): Int = this xor (1 shl i)

// #################################################################################################

class TravellingSalesman<T>(
    private val n: Int,
    private val initialCost: IntArray,
    private val costs: Array<IntArray>,
) {
    private val powN = 1 shl n

    fun solve(): Array<IntArray> {
        val f = Array(powN) { IntArray(n) { -1 } }
        for (i in 0 until n) f[1 shl i][i] = initialCost[i]
        for (x in 1 until powN) for (i in 0 until n) if (x hasBit i) {
            val y = x xorBit i
            for (j in 0 until n) if (y hasBit j && f[y][j] != -1 && costs[j][i] != -1) {
                if (f[x][i] == -1 || f[x][i] > f[y][j] + costs[j][i]) {
                    f[x][i] = f[y][j] + costs[j][i]
                }
            }
        }
        return f
    }

    fun maxVisited(isValid: (cost: Int, finalPosition: Int) -> Boolean): Int {
        val f = solve()
        var ans = 0
        for (x in 1 until powN) for (i in 0 until n) if (x hasBit i && f[x][i] != -1) {
            val c = Integer.bitCount(x)
            if (c > ans && isValid(f[x][i], i)) ans = c
        }
        return ans
    }
}
