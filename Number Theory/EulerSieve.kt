class EulerSieve(private val n: Int) {
    private val isPrime = BitSet(n + 1)
    val primes = mutableListOf<Int>()

    init {
        isPrime.set(2, n + 1)
        for (i in 2..n) {
            if (isPrime[i]) primes += i
            for (j in primes) {
                if (i * j > n) break
                isPrime.clear(i * j)
                if (i % j == 0) break
            }
        }
    }

    fun factorization(number: Int): List<Pair<Int, Int>> = buildList {
        var x = number
        for (prime in primes) {
            if (prime > x) break
            var y = 0
            while (x % prime == 0) {
                x /= prime
                y++
            }
            if (y > 0) add(prime to y)
        }
        if (x > 1) add(x to 1)
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
        if (number <= n) return isPrime[number]
        // For number > n, check divisibility by primes up to n
        // This is accurate if number <= n*n
        // This may incorrectly return true if number > n*n and its smallest prime factor is > n
        for (p in primes) {
            if (1L * p * p > number) break // Optimization: if p*p > number, no need to check further
            if (number % p == 0) return false // Found a prime factor <= n
        }
        return true // No prime factors <= n found
    }
}

// O(n) time & space

val e = EulerSieve(1000)     //   168 primes
val e = EulerSieve(10000)    //  1229 primes
val e = EulerSieve(100000)   //  9592 primes
val e = EulerSieve(1000000)  // 78498 primes
