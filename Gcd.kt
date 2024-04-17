tailrec fun gcd(a: Int, b: Int): Int {
    if (b == 0) return a
    return gcd(b, a % b)
}

// Overflow possible
fun lcm(a: Int, b: Int): Int = a / gcd(a, b) * b

tailrec fun gcd(a: Long, b: Long): Long {
    if (b == 0L) return a
    return gcd(b, a % b)
}

// Overflow possible
fun lcm(a: Long, b: Long): Long = a / gcd(a, b) * b
