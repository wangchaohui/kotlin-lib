data class MaximumAndCount(
    /** Maximum to count, sort descending */
    val max: List<Pair<Int, Int>>,
) {
    companion object {
        fun merge(a: MaximumAndCount, b: MaximumAndCount): MaximumAndCount {
            var aIndex = 0
            var bIndex = 0
            val max = mutableListOf<Pair<Int, Int>>()
            while (max.size < LIMIT) {
                val aMax = a.max.getOrNull(aIndex)
                val bMax = b.max.getOrNull(bIndex)
                when {
                    aMax != null && bMax != null && aMax.first == bMax.first -> {
                        max += aMax.first to aMax.second + bMax.second
                        aIndex++
                        bIndex++
                    }

                    aMax != null && (bMax == null || aMax.first > bMax.first) -> {
                        max += aMax
                        aIndex++
                    }

                    bMax != null -> {
                        max += bMax
                        bIndex++
                    }

                    else -> break
                }
            }
            return MaximumAndCount(max)
        }

        private const val LIMIT = 2
    }
}
