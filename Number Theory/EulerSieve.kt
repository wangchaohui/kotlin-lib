class EulerSieve(n: Int) {
    private val isPrime = BitSet(n + 1)
    val primes = mutableListOf<Int>()

    init {
        isPrime.set(2, n)
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
}

// O(n) time & space

val e = EulerSieve(1000)     //   168 primes
val e = EulerSieve(10000)    //  1229 primes
val e = EulerSieve(100000)   //  9592 primes
val e = EulerSieve(1000000)  // 78498 primes
