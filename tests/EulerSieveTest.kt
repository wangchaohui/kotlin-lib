import kotlin.test.*
import java.util.BitSet // Required for the dummy EulerSieve

// Dummy implementation of EulerSieve to make tests compilable standalone.
// In a real scenario, this would be imported from the source set.
class EulerSieve(private val n: Int) {
    private val isPrimeBitSet = BitSet(n + 1) // Renamed to avoid clash
    val primes = mutableListOf<Int>()

    init {
        if (n >= 2) isPrimeBitSet.set(2, n + 1)
        for (i in 2..n) {
            if (isPrimeBitSet[i]) primes += i
            for (j in primes) {
                if (i * j > n) break
                isPrimeBitSet.clear(i * j)
                if (i % j == 0) break
            }
        }
    }

    fun factorization(number: Int): List<Pair<Int, Int>> = buildList {
        var x = number
        if (x <= 0) return@buildList // Or throw, depending on desired behavior for non-positives
        for (prime in primes) {
            if (prime * prime > x && x > 1 && !isPrime(x)) { // Optimization: if x > 1 and x is composite and prime*prime > x, the remaining x must be prime.
                 // This optimization is tricky with the current isPrime for x > n.
                 // Let's stick to simpler logic for the dummy matching original code.
            }
            if (prime > x && x > 1 && prime*prime > x ) break // if prime > sqrt(x), no more factors from list
            if (prime > x) break // if current prime from list is already greater than remaining x

            var y = 0
            while (x % prime == 0) {
                x /= prime
                y++
            }
            if (y > 0) add(prime to y)
        }
        if (x > 1) add(x to 1) // Remaining x is a prime factor itself (possibly > n)
    }
    
    /**
     * Checks if a given number is prime.
     *
     * The `isPrime(number)` method is accurate for `number <= n`. For `number > n`, it checks
     * divisibility by primes up to `n`. This means it can correctly identify a number as prime
     * if all its prime factors are greater than `n`, provided `number <= n*n`. However, if
     * `number > n*n` and its smallest prime factor is also `> n`, this method may incorrectly
     * return `true`. For a guaranteed primality test for numbers `> n`, a different algorithm
     * (e.g., Miller-Rabin) should be used if `n` is not large enough (i.e. `n < sqrt(number)`).
     *
     * @param number The number to check for primality.
     * @return `true` if the number is prime according to the described logic, `false` otherwise.
     */
    fun isPrime(number: Int): Boolean {
        if (number <= 1) return false
        if (number <= n) return isPrimeBitSet[number]
        // For number > n, check divisibility by primes up to n
        // This is accurate if number <= n*n or if smallest prime factor of number is <= n
        // This may incorrectly return true if number > n*n and its smallest prime factor is > n
        for (p in primes) {
            if (1L * p * p > number) break // Optimization: if p*p > number, no need to check further for factors from the list
            if (number % p == 0) return false // Found a prime factor <= n
        }
        return true // No prime factors <= n found. Number might be prime or composite with all factors > n.
    }
}


class EulerSieveTest {
    private val sieve = EulerSieve(100)

    @Test
    fun testIsPrime_lessThanOrEqualToN() {
        // Primes <= 100
        assertTrue(sieve.isPrime(2))
        assertTrue(sieve.isPrime(3))
        assertTrue(sieve.isPrime(7))
        assertTrue(sieve.isPrime(13))
        assertTrue(sieve.isPrime(89))
        assertTrue(sieve.isPrime(97))

        // Non-primes <= 100
        assertFalse(sieve.isPrime(1))
        assertFalse(sieve.isPrime(4))
        assertFalse(sieve.isPrime(6))
        assertFalse(sieve.isPrime(25))
        assertFalse(sieve.isPrime(91)) // 7 * 13
        assertFalse(sieve.isPrime(100))

        // Edge cases for isPrime
        assertFalse(sieve.isPrime(0))
        assertFalse(sieve.isPrime(-5))
    }

    @Test
    fun testIsPrime_greaterThanN() {
        // Small prime > n (100)
        // 101 is prime. n=100. primes list goes up to 97.
        // Loop for p in primes: 1L * p * p > 101. Smallest p=2, 2*2=4. Largest p=97, 97*97 = 9409 > 101.
        // Loop will break when p*p > 101. For p=11, 11*11=121 > 101.
        // No p in primes up to 10 will divide 101. Returns true. Correct.
        assertTrue(sieve.isPrime(101))

        // Composite c = p1*p2 where p1,p2 <= n but c > n
        // 7 * 17 = 119. n=100.
        // Loop for p in primes: p=7. 7*7=49 < 119. 119 % 7 == 0. Returns false. Correct.
        assertFalse(sieve.isPrime(119)) // 7 * 17

        // Composite c = p1*p2 where p1 <= n, p2 > n, and c > n
        // 3 * 101 = 303. n=100.
        // Loop for p in primes: p=3. 3*3=9 < 303. 303 % 3 == 0. Returns false. Correct.
        assertFalse(sieve.isPrime(303)) // 3 * 101
    }

    @Test
    fun testIsPrime_limitationForNumberGreaterThanNSquared() {
        // Illustrate documented limitation
        // Pick two primes p1, p2 > n=100. e.g., 101, 103.
        // c = 101 * 103 = 10403.
        // n=100. Smallest prime factor of c is 101, which is > n.
        // c (10403) > n*n (100*100 = 10000).
        // The isPrime method will check divisibility by primes in `sieve.primes` (up to 97).
        // For p=97 (largest prime in sieve.primes), 97*97 = 9409, which is not > 10403.
        // So, the loop `for (p in primes)` will complete without finding a factor.
        // The method will return true. This is the documented limitation.
        val c = 101 * 103 // = 10403
        assertTrue(sieve.isPrime(c), "Expected true for 10403 (101*103) with n=100 due to documented limitation for numbers > n*n with SPF > n.")
    }

    @Test
    fun testFactorization_basicCases() {
        assertEquals(listOf(2 to 2, 3 to 1), sieve.factorization(12))
        assertEquals(listOf(7 to 1), sieve.factorization(7))
        assertEquals(emptyList(), sieve.factorization(1))
    }

    @Test
    fun testFactorization_productOfPrimes() {
        // 2 * 3 * 5 = 30
        assertEquals(listOf(2 to 1, 3 to 1, 5 to 1), sieve.factorization(30))
        // 7 * 11 * 13 = 1001. 1001 > n=100. Primes 7,11,13 are <= n.
        assertEquals(listOf(7 to 1, 11 to 1, 13 to 1), sieve.factorization(1001))
    }

    @Test
    fun testFactorization_numberGreaterThanN_withFactorGreaterThanN() {
        // 202 = 2 * 101. n=100.
        // '2' is in primes. x becomes 101.
        // Loop finishes for primes list. 'x' (101) > 1. So (101, 1) is added.
        assertEquals(listOf(2 to 1, 101 to 1), sieve.factorization(202))

        // 3 * 101 = 303
        assertEquals(listOf(3 to 1, 101 to 1), sieve.factorization(303))

        // A number whose smallest prime factor is > n
        // 101 * 103 = 10403. n=100.
        // No prime in `primes` (up to 97) will divide 10403.
        // `x` remains 10403. `add(10403 to 1)`
        assertEquals(listOf(10403 to 1), sieve.factorization(10403))
    }
    
    @Test
    fun testFactorization_numberWithLargePrimeFactorSquared() {
        // Test a number like p*p where p > n
        // e.g. 101*101 = 10201. n=100
        // Factorization should return [(10201, 1)] because 10201 is not divisible by any prime <= 100
        assertEquals(listOf(10201 to 1), sieve.factorization(10201))
    }

    @Test
    fun testFactorization_numberIsPrimeAndGreaterThanN() {
        // 101 is prime and > 100.
        assertEquals(listOf(101 to 1), sieve.factorization(101))
    }
}
