import kotlin.test.*
import kotlin.math.abs

// Dummy implementations of the original functions to make tests compilable standalone.
// In a real scenario, these would be imported from the source set.

tailrec fun gcd(a: Int, b: Int): Int {
    if (b == 0) return abs(a)
    return gcd(b, a % b)
}

fun lcm(a: Int, b: Int): Int {
    if (a == 0 || b == 0) return 0
    val common = gcd(a, b)
    // The comment about overflow was here. It's still possible if 'a / common * b' overflows.
    // For example, if common = 1, a = Int.MAX_VALUE, b = 2.
    // However, the new implementation is better than a * b / common.
    return abs(a / common * b)
}

tailrec fun gcd(a: Long, b: Long): Long {
    if (b == 0L) return abs(a)
    return gcd(b, a % b)
}

fun lcm(a: Long, b: Long): Long {
    if (a == 0L || b == 0L) return 0L
    val common = gcd(a, b)
    return abs(a / common * b)
}


class GcdTest {

    // gcd(Int, Int) Tests
    @Test
    fun testGcdInt_positiveInputs() {
        assertEquals(6, gcd(48, 18))
        assertEquals(6, gcd(18, 48))
        assertEquals(1, gcd(17, 5))
        assertEquals(5, gcd(5, 10))
    }

    @Test
    fun testGcdInt_negativeInputs() {
        assertEquals(6, gcd(-48, 18))
        assertEquals(6, gcd(48, -18))
        assertEquals(6, gcd(-48, -18))
    }

    @Test
    fun testGcdInt_zeroInputs() {
        assertEquals(5, gcd(5, 0))
        assertEquals(5, gcd(0, 5))
        assertEquals(5, gcd(-5, 0))
        assertEquals(5, gcd(0, -5))
        assertEquals(0, gcd(0, 0))
    }

    // gcd(Long, Long) Tests
    @Test
    fun testGcdLong_positiveInputs() {
        assertEquals(6L, gcd(48L, 18L))
        assertEquals(6L, gcd(18L, 48L))
    }

    @Test
    fun testGcdLong_negativeInputs() {
        assertEquals(6L, gcd(-48L, 18L))
        assertEquals(6L, gcd(48L, -18L))
        assertEquals(6L, gcd(-48L, -18L))
    }

    @Test
    fun testGcdLong_zeroInputs() {
        assertEquals(5L, gcd(5L, 0L))
        assertEquals(5L, gcd(0L, 5L))
        assertEquals(5L, gcd(-5L, 0L))
        assertEquals(5L, gcd(0L, -5L))
        assertEquals(0L, gcd(0L, 0L))
    }

    // lcm(Int, Int) Tests
    @Test
    fun testLcmInt_positiveInputs() {
        assertEquals(36, lcm(12, 18))
        assertEquals(72, lcm(24, 18))
    }

    @Test
    fun testLcmInt_negativeInputs() {
        assertEquals(36, lcm(-12, 18))
        assertEquals(36, lcm(12, -18))
        assertEquals(36, lcm(-12, -18))
    }

    @Test
    fun testLcmInt_zeroInputs() {
        assertEquals(0, lcm(5, 0))
        assertEquals(0, lcm(0, 5))
        assertEquals(0, lcm(0, 0))
    }

    @Test
    fun testLcmInt_propertyLcmGcd() {
        val pairs = listOf(12 to 18, 5 to 7, 1 to 1, 100 to 1, 1 to 100, 24 to 18)
        for ((a, b) in pairs) {
            val expected = abs(a.toLong() * b.toLong())
            val actual = lcm(a, b).toLong() * gcd(a, b).toLong()
            assertEquals(expected, actual, "lcm($a, $b) * gcd($a, $b) == abs($a * $b)")
        }
    }
    
    @Test
    fun testLcmInt_potentialOverflowButResultFits() {
        // gcd(50000, 40000) = 10000
        // lcm = (50000 / 10000) * 40000 = 5 * 40000 = 200000. This fits in Int.
        // (50000 * 40000) would overflow Int.
        assertEquals(200000, lcm(50000, 40000))
        assertEquals(200000, lcm(40000, 50000))

        // Example from problem: lcm(Int.MAX_VALUE, Int.MAX_VALUE -1)
        // gcd(Int.MAX_VALUE, Int.MAX_VALUE - 1) = gcd(2147483647, 2147483646) = 1
        // lcm = (2147483647 / 1) * 2147483646. This will overflow.
        // The test below is for a case where the final LCM *does* fit.
        val a = 3 * 5 * 7 * 11 * 13 * 17 * 19 // = 969969
        val b = 3 * 5 * 7 * 11 * 13 * 17 * 23 // = 1210701
        // gcd = 3*5*7*11*13*17 = 46189
        // lcm = a * 23 = 22309287 or b * 19 = 22903319 (error in manual calc, should be same)
        // lcm = (a/gcd) * b = 19 * b = 19 * 1210701 = 22903319
        // lcm = (b/gcd) * a = 23 * a = 23 * 969969 = 22309287
        // Ah, my example numbers a and b for custom calculation are wrong.
        // Let's stick to the 50000, 40000 example which is clear.

        // Another example:
        // a = 2*2*2*2*2*2*2*2 * 3 = 256 * 3 = 768
        // b = 2*2*2*2*2*2*2*2 * 5 = 256 * 5 = 1280
        // gcd = 256
        // lcm = (768/256)*1280 = 3 * 1280 = 3840
        // lcm = (1280/256)*768 = 5 * 768 = 3840
        // 768 * 1280 = 983040.
        // The result fits in int.
        assertEquals(3840, lcm(768, 1280))
    }

    // lcm(Long, Long) Tests
    @Test
    fun testLcmLong_positiveInputs() {
        assertEquals(36L, lcm(12L, 18L))
    }

    @Test
    fun testLcmLong_negativeInputs() {
        assertEquals(36L, lcm(-12L, 18L))
        assertEquals(36L, lcm(12L, -18L))
        assertEquals(36L, lcm(-12L, -18L))
    }

    @Test
    fun testLcmLong_zeroInputs() {
        assertEquals(0L, lcm(5L, 0L))
        assertEquals(0L, lcm(0L, 5L))
        assertEquals(0L, lcm(0L, 0L))
    }

    @Test
    fun testLcmLong_propertyLcmGcd() {
        val pairs = listOf(12L to 18L, 5L to 7L, 1L to 1L, 500000L to 400000L)
        for ((a, b) in pairs) {
            val expected = abs(a * b) // Direct multiplication is fine for Long for these test values
            val actual = lcm(a, b) * gcd(a, b)
            assertEquals(expected, actual, "lcm($a, $b) * gcd($a, $b) == abs($a * $b)")
        }
    }

    @Test
    fun testLcmLong_potentialOverflowButResultFits() {
        // Similar to Int, but with Long values
        // lcm(500_000_000L, 400_000_000L) = 2_000_000_000L
        // gcd = 100_000_000L
        // (500M / 100M) * 400M = 5 * 400M = 2_000_000_000L
        assertEquals(2_000_000_000L, lcm(500_000_000L, 400_000_000L))
        
        // Max values
        // val largeA = Long.MAX_VALUE / 2
        // val largeB = Long.MAX_VALUE / 3
        // val common = gcd(largeA, largeB)
        // val expectedLcm = abs(largeA / common * largeB)
        // assertEquals(expectedLcm, lcm(largeA, largeB))
        // This test is a bit complex to set up without knowing gcd of MAX_VALUE/2 and MAX_VALUE/3
        // The 500M, 400M example is sufficient.
    }
}
