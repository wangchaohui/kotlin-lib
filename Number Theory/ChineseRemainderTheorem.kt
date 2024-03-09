/**
 * To solve ax + by = gcd(a, b)
 */
object ExtendedEuclidean {
    data class Answer(
        val gcd: Long,
        val x: Long,
        val y: Long,
    )

    fun solve(a: Long, b: Long): Answer {
        if (b == 0L) return Answer(a, 1, 0)
        val (gcd, x, y) = solve(b, a % b)
        return Answer(gcd, y, x - a / b * y)
    }
}

/**
 * To solve ax = 1 (mod m)
 *
 * A solution exists if and only if gcd(a, m) = 1
 */
object ModularMultiplicativeInverse {
    fun solve(a: Long, m: Long): Long? {
        val (gcd, x) = ExtendedEuclidean.solve(a, m)
        if (gcd != 1L) return null
        return x.mod(m)
    }
}

/**
 * The Chinese remainder theorem asserts that if the modulus are pairwise coprime,
 * and if 0 ≤ remainder < modulus for every congruence, then there is one and only
 * one answer in `[0, product of all modulus)`.
 */
object ChineseRemainderTheorem {
    data class Congruence(
        val remainder: Long,
        val modulus: Long,
    )

    fun solve(congruences: List<Congruence>): Long {
        val productOfAllModulus = congruences.fold(1L) { product, congruence ->
            Math.multiplyExact(product, congruence.modulus)
        }
        return congruences.fold(0L) { acc, (remainder, modulus) ->
            val m = productOfAllModulus / modulus
            val mInverse = ModularMultiplicativeInverse.solve(m, modulus)!!
            (acc + (remainder * m * mInverse).mod(productOfAllModulus)).mod(productOfAllModulus)
        }
    }
}
