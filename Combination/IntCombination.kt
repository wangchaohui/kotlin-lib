object IntCombination {
    const val MAX_N = 33 // Combination up to 33 can fit into Int
    val c = Array(MAX_N + 1) { IntArray(MAX_N + 1) }

    init {
        for (i in 0..MAX_N) {
            c[i][0] = 1
            for (j in 1..<i) {
                c[i][j] = c[i - 1][j - 1] + c[i - 1][j]
            }
            c[i][i] = 1
        }
    }
}
