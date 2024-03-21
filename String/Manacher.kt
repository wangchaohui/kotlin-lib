class Manacher(private val string: String) {
    // Preprocess the input string (insert sentinels and handle boundaries)
    private val processedString = preProcess(string)

    // Array to store the length of LPS centered at each position
    val lps = IntArray(processedString.length)

    init {
        var center = 1
        var rightBoundary = 1

        for (i in 2 until processedString.length - 2) {
            // Check if we can expand LPS using existing information
            if (i < rightBoundary) {
                // Mirror index from the current center
                val mirrorIndex = 2 * center - i
                lps[i] = minOf(rightBoundary - i, lps[mirrorIndex])
            }

            // Expand from the current center for odd length palindromes
            while (processedString[i + lps[i] + 1] == processedString[i - lps[i] - 1]) lps[i]++

            // Update center and rightBoundary if a longer LPS is found
            if (i + lps[i] > rightBoundary) {
                center = i
                rightBoundary = i + lps[i]
            }
        }
    }

    /** Find the longest palindrome in the original string */
    fun findLongestPalindrome(): String {
        val centerIndex = (1 until processedString.length - 1).maxBy { lps[it] }
        return findLongestPalindrome(centerIndex)
    }

    /**
     * Find the longest palindrome in the original string with [centerIndex].
     *
     *                 0     1     2     3     4     5     6     7
     *                 a     b     c     d     e     f     g     h
     * [centerIndex]   2  3  4  5  6  7  8  9 10 11 12 13 14 15 16
     */
    fun findLongestPalindrome(centerIndex: Int): String {
        val maxLength = lps[centerIndex]
        val start = (centerIndex - maxLength - 1) / 2
        return string.substring(start, start + maxLength)
    }

    private fun preProcess(s: String) = buildString {
        append("^") // Sentinel character at the start
        for (char in s) append("#$char")
        append("#$") // Sentinel character at the end
    }
}
