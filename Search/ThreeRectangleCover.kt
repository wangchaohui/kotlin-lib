class ThreeRectangleCover(private val score: (iBegin: Int, iEnd: Int, jBegin: Int, jEnd: Int) -> Int) {

    fun oneRectangleCover(iBegin: Int, iEnd: Int, jBegin: Int, jEnd: Int): Int = score(iBegin, iEnd, jBegin, jEnd)

    /**
     * Gets the minimum score if the grid `[iBegin, iEnd) x [jBegin, jEnd)` is covered by two rectangles.
     */
    fun twoRectangleCover(iBegin: Int, iEnd: Int, jBegin: Int, jEnd: Int): Int {
        if (iBegin + 1 >= iEnd && jBegin + 1 >= jEnd) return oneRectangleCover(iBegin, iEnd, jBegin, jEnd)
        var minScore = Int.MAX_VALUE
        for (i in iBegin + 1..<iEnd) {
            val a = oneRectangleCover(iBegin, i, jBegin, jEnd)
            val b = oneRectangleCover(i, iEnd, jBegin, jEnd)
            minScore = minScore.coerceAtMost(a + b)
        }
        for (j in jBegin + 1..<jEnd) {
            val a = oneRectangleCover(iBegin, iEnd, jBegin, j)
            val b = oneRectangleCover(iBegin, iEnd, j, jEnd)
            minScore = minScore.coerceAtMost(a + b)
        }
        return minScore
    }

    /**
     * Gets the minimum score if the grid `[iBegin, iEnd) x [jBegin, jEnd)` is covered by three rectangles.
     */
    fun threeRectangleCover(iBegin: Int, iEnd: Int, jBegin: Int, jEnd: Int): Int {
        if (iBegin + 1 >= iEnd && jBegin + 1 >= jEnd) return oneRectangleCover(iBegin, iEnd, jBegin, jEnd)
        var minScore = Int.MAX_VALUE
        for (i in iBegin + 1..<iEnd) {
            val a1 = oneRectangleCover(iBegin, i, jBegin, jEnd)
            val a2 = twoRectangleCover(iBegin, i, jBegin, jEnd)
            val b1 = oneRectangleCover(i, iEnd, jBegin, jEnd)
            val b2 = twoRectangleCover(i, iEnd, jBegin, jEnd)
            minScore = minScore.coerceAtMost(a1 + b2).coerceAtMost(a2 + b1)
        }
        for (j in jBegin + 1..<jEnd) {
            val a1 = oneRectangleCover(iBegin, iEnd, jBegin, j)
            val a2 = twoRectangleCover(iBegin, iEnd, jBegin, j)
            val b1 = oneRectangleCover(iBegin, iEnd, j, jEnd)
            val b2 = twoRectangleCover(iBegin, iEnd, j, jEnd)
            minScore = minScore.coerceAtMost(a1 + b2).coerceAtMost(a2 + b1)
        }
        return minScore
    }
}
