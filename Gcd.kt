tailrec fun gcd(a: Int, b: Int): Int {
    if (b == 0) return kotlin.math.abs(a)
    return gcd(b, a % b)
}

// Overflow possible
fun lcm(a: Int, b: Int): Int {
    if (a == 0 || b == 0) return 0
    val common = gcd(a, b)
    return kotlin.math.abs(a / common * b)
}

tailrec fun gcd(a: Long, b: Long): Long {
    if (b == 0L) return kotlin.math.abs(a)
    return gcd(b, a % b)
}

// Overflow possible
fun lcm(a: Long, b: Long): Long {
    if (a == 0L || b == 0L) return 0L
    val common = gcd(a, b)
    return kotlin.math.abs(a / common * b)
}
