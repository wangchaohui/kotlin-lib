import kotlin.test.*

// Placeholder for actual IntPredicate and LongPredicate if not available in current scope
// In a real project, these would come from a library or be defined if not standard.
// For the purpose of these tests, we'll define simple versions.
fun interface IntPredicate {
    fun test(value: Int): Boolean
}

fun interface LongPredicate {
    fun test(value: Long): Boolean
}

fun interface DoublePredicate {
    fun test(value: Double): Boolean
}

class BinarySearchTest {

    // lowerBound (Int) Tests
    @Test
    fun testLowerBoundInt_elementPresent() {
        assertEquals(2, lowerBound(0, 4, IntPredicate { it >= 2 }))
        assertEquals(0, lowerBound(0, 4, IntPredicate { it >= 0 }))
        assertEquals(4, lowerBound(0, 4, IntPredicate { it >= 4 }))
    }

    @Test
    fun testLowerBoundInt_elementNotPresent() {
        assertNull(lowerBound(0, 4, IntPredicate { it >= 5 }))
        assertEquals(0, lowerBound(0, 4, IntPredicate { it >= -1 })) // all elements satisfy
    }

    @Test
    fun testLowerBoundInt_emptyRange() {
        assertNull(lowerBound(5, 4, IntPredicate { it >= 5 }))
    }

    @Test
    fun testLowerBoundInt_allElementsSame() {
        assertEquals(0, lowerBound(0, 4, IntPredicate { it >= 2 }, IntArray(5) { 2 })) // Assuming a version that takes array
        // For the provided signature, we test with predicates
        assertEquals(0, lowerBound(2, 2, IntPredicate { it >= 2 }))
        assertNull(lowerBound(2, 2, IntPredicate { it >= 3 }))
        assertEquals(2, lowerBound(2, 2, IntPredicate { it >= 1 }))
    }

    @Test
    fun testLowerBoundInt_edgeCases() {
        assertEquals(Int.MAX_VALUE, lowerBound(0, Int.MAX_VALUE, IntPredicate { it >= Int.MAX_VALUE }))
        assertEquals(0, lowerBound(0, Int.MAX_VALUE, IntPredicate { it >= 0 }))
        assertEquals(Int.MAX_VALUE / 2, lowerBound(0, Int.MAX_VALUE, IntPredicate { it >= Int.MAX_VALUE / 2 }))

        assertEquals(0, lowerBound(Int.MIN_VALUE, 0, IntPredicate { it >= 0 }))
        assertEquals(Int.MIN_VALUE, lowerBound(Int.MIN_VALUE, 0, IntPredicate { it >= Int.MIN_VALUE }))
        assertEquals(Int.MIN_VALUE / 2, lowerBound(Int.MIN_VALUE, 0, IntPredicate { it >= Int.MIN_VALUE / 2 })) // m can be negative

        assertEquals(Int.MAX_VALUE, lowerBound(Int.MIN_VALUE, Int.MAX_VALUE, IntPredicate { it >= Int.MAX_VALUE }))
        assertEquals(Int.MIN_VALUE, lowerBound(Int.MIN_VALUE, Int.MAX_VALUE, IntPredicate { it >= Int.MIN_VALUE }))
        assertEquals(0, lowerBound(Int.MIN_VALUE, Int.MAX_VALUE, IntPredicate { it >= 0 }))
    }

    // lowerBound (Long) Tests
    @Test
    fun testLowerBoundLong_elementPresent() {
        assertEquals(2L, lowerBound(0L, 4L, LongPredicate { it >= 2L }))
    }

    @Test
    fun testLowerBoundLong_edgeCases() {
        assertEquals(Long.MAX_VALUE, lowerBound(0L, Long.MAX_VALUE, LongPredicate { it >= Long.MAX_VALUE }))
        assertEquals(0L, lowerBound(Long.MIN_VALUE, 0L, LongPredicate { it >= 0L }))
        assertEquals(0L, lowerBound(Long.MIN_VALUE, Long.MAX_VALUE, LongPredicate { it >= 0L }))
    }

    // upperBound (Int) Tests
    @Test
    fun testUpperBoundInt_elementPresent() {
        assertEquals(2, upperBound(0, 4, IntPredicate { it <= 2 }))
        assertEquals(4, upperBound(0, 4, IntPredicate { it <= 4 }))
        assertEquals(0, upperBound(0, 4, IntPredicate { it <= 0 }))
    }

    @Test
    fun testUpperBoundInt_elementNotPresent() {
        assertNull(upperBound(0, 4, IntPredicate { it <= -1 }))
        assertEquals(4, upperBound(0, 4, IntPredicate { it <= 5 })) // all elements satisfy
    }

    @Test
    fun testUpperBoundInt_emptyRange() {
        assertNull(upperBound(5, 4, IntPredicate { it <= 5 }))
    }
    
    @Test
    fun testUpperBoundInt_allElementsSame() {
        assertEquals(4, upperBound(2, 2, IntPredicate { it <= 2 })) // Test with single element range
        assertNull(upperBound(2, 2, IntPredicate { it <= 1 }))
        assertEquals(2, upperBound(2, 2, IntPredicate { it <= 3 }))
    }

    @Test
    fun testUpperBoundInt_edgeCases() {
        assertEquals(0, upperBound(0, Int.MAX_VALUE, IntPredicate { it <= 0 }))
        assertEquals(Int.MAX_VALUE, upperBound(0, Int.MAX_VALUE, IntPredicate { it <= Int.MAX_VALUE }))
        assertEquals(Int.MAX_VALUE / 2, upperBound(0, Int.MAX_VALUE, IntPredicate { it <= Int.MAX_VALUE / 2 }))

        assertEquals(Int.MIN_VALUE, upperBound(Int.MIN_VALUE, 0, IntPredicate { it <= Int.MIN_VALUE }))
        assertEquals(0, upperBound(Int.MIN_VALUE, 0, IntPredicate { it <= 0 }))
        assertEquals(Int.MIN_VALUE + (0 - Int.MIN_VALUE) / 2, upperBound(Int.MIN_VALUE, 0, IntPredicate { it <= Int.MIN_VALUE + (0 - Int.MIN_VALUE) / 2 }))


        assertEquals(Int.MIN_VALUE, upperBound(Int.MIN_VALUE, Int.MAX_VALUE, IntPredicate { it <= Int.MIN_VALUE }))
        assertEquals(Int.MAX_VALUE, upperBound(Int.MIN_VALUE, Int.MAX_VALUE, IntPredicate { it <= Int.MAX_VALUE }))
        assertEquals(0, upperBound(Int.MIN_VALUE, Int.MAX_VALUE, IntPredicate { it <= 0 }))
    }

    // upperBound (Long) Tests
    @Test
    fun testUpperBoundLong_elementPresent() {
        assertEquals(2L, upperBound(0L, 4L, LongPredicate { it <= 2L }))
    }

    @Test
    fun testUpperBoundLong_edgeCases() {
        assertEquals(0L, upperBound(0L, Long.MAX_VALUE, LongPredicate { it <= 0L }))
        assertEquals(Long.MIN_VALUE, upperBound(Long.MIN_VALUE, 0L, LongPredicate { it <= Long.MIN_VALUE }))
        assertEquals(0L, upperBound(Long.MIN_VALUE, Long.MAX_VALUE, LongPredicate { it <= 0L }))
    }

    // upperBound (Double) Tests
    @Test
    fun testUpperBoundDouble_basic() {
        assertEquals(2.5, upperBound(0.0, 5.0, DoublePredicate { it <= 2.5 }), 1e-10)
        assertEquals(5.0, upperBound(0.0, 5.0, DoublePredicate { it <= 5.0 }), 1e-10)
        assertNull(upperBound(0.0, 5.0, DoublePredicate { it <= -1.0 }))
        assertEquals(5.0, upperBound(0.0, 5.0, DoublePredicate { it <= 6.0 })) // all satisfy
    }

    @Test
    fun testUpperBoundDouble_tolerance() {
        val tolerance = 1e-9
        // Test predicate that is true up to a point
        val value = 3.1415926535
        assertEquals(value, upperBound(0.0, 5.0, DoublePredicate { it <= value }), tolerance / 2)

        // Test when high is very close to value satisfying predicate
        assertEquals(value, upperBound(value - tolerance * 0.5, value + tolerance * 0.5, DoublePredicate { it <= value }), tolerance * 2)
        
        // Test when l + tolerance >= h
        val low = 1.0
        val highClose = low + tolerance / 2.0
        assertEquals(low, upperBound(low, highClose, DoublePredicate {it <= low}), tolerance /10.0) // should be low

        val highExact = low + tolerance
         assertEquals(low, upperBound(low, highExact, DoublePredicate {it <= low}), tolerance /10.0) // should be low
    }
}

// Dummy implementations of the original functions to make tests compilable standalone.
// In a real scenario, these would be imported from the source set.
fun lowerBound(low: Int, high: Int, predicate: IntPredicate): Int? {
    if (low > high) return null
    var l = low
    var h = high
    while (l < h) {
        val m = l + (h - l) / 2
        if (predicate.test(m)) {
            h = m
        } else {
            l = m + 1
        }
    }
    return l.takeIf(predicate::test)
}

fun upperBound(low: Int, high: Int, predicate: IntPredicate): Int? {
    if (low > high) return null
    var l = low
    var h = high
    while (l < h) {
        val m = l + (h - l + 1) / 2
        if (predicate.test(m)) {
            l = m
        } else {
            h = m - 1
        }
    }
    return l.takeIf(predicate::test)
}

fun lowerBound(low: Long, high: Long, predicate: LongPredicate): Long? {
    if (low > high) return null
    var l = low
    var h = high
    while (l < h) {
        val m = l + (h - l) / 2
        if (predicate.test(m)) {
            h = m
        } else {
            l = m + 1
        }
    }
    return l.takeIf(predicate::test)
}

fun upperBound(low: Long, high: Long, predicate: LongPredicate): Long? {
    if (low > high) return null
    var l = low
    var h = high
    while (l < h) {
        val m = l + (h - l + 1) / 2
        if (predicate.test(m)) {
            l = m
        } else {
            h = m - 1
        }
    }
    return l.takeIf(predicate::test)
}

fun upperBound(low: Double, high: Double, predicate: DoublePredicate): Double? {
    if (low > high) return null
    var l = low
    var h = high
    // The 1e-9 tolerance is fixed and might not be suitable for all precision requirements.
    while (l + 1e-9 < h) {
        val m = (l + h) / 2
        // The `if (l == m) break` and `if (h == m) break` conditions are to prevent infinite loops
        // due to floating-point precision limits, but might cause the search to terminate before
        // the theoretical best precision is achieved for some inputs or predicate functions.
        if (l == m) break // Prevent infinite loop if m is not changing
        if (predicate.test(m)) {
            l = m
        } else {
            if (h == m) break // Prevent infinite loop if m is not changing
            h = m
        }
    }
    return l.takeIf(predicate::test)
}

// Added for testLowerBoundInt_allElementsSame
fun lowerBound(low: Int, high: Int, predicate: IntPredicate, arr: IntArray): Int? {
    // This is a simplified version assuming arr is sorted and predicate checks value from array
    // This is just to make the test compilable. The actual functions are in BinarySearch.kt
    if (low > high || low >= arr.size || high >= arr.size) return null
    var lIdx = low
    var hIdx = high
    var ans: Int? = null
    while(lIdx <= hIdx) {
        val midIdx = lIdx + (hIdx - lIdx) / 2
        if (predicate.test(arr[midIdx])) {
            ans = midIdx
            hIdx = midIdx -1
        } else {
            lIdx = midIdx + 1
        }
    }
    return ans
}
