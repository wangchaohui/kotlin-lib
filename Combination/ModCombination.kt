class ModCombination(n: Int) {
    val factorial = Array(n + 1) { ModInt.One }.apply {
        for (i in 1..n) this[i] = this[i - 1] * i
    }
    val factorialInv = Array(n + 1) { ModInt.One }.apply {
        this[n] = factorial[n].inv()
        for (i in n downTo 1) this[i - 1] = this[i] * i
    }

    fun c(a: Int, b: Int) =
        if (a >= b) factorial[a] * factorialInv[b] * factorialInv[a - b] else ModInt.Zero
}

val mc = ModCombination(400000)
