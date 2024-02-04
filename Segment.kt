data class Segment(val first: Long = Long.MIN_VALUE, val last: Long = Long.MAX_VALUE) {
    fun intersect(other: Segment) = Segment(first.coerceAtLeast(other.first), last.coerceAtMost(other.last))
    val points: Long
        get() = when {
            first > last -> 0
            first == Long.MIN_VALUE || last == Long.MAX_VALUE -> Long.MAX_VALUE
            else -> last - first + 1
        }
}
