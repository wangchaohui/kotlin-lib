data class Complex(
    val re: Double = 0.0,
    val im: Double = 0.0,
) {
    operator fun plus(other: Complex) = Complex(re + other.re, im + other.im)
    operator fun minus(other: Complex) = Complex(re - other.re, im - other.im)
    operator fun times(other: Double) = Complex(re * other, im * other)
    operator fun times(other: Complex) = Complex(
        re = re * other.re - im * other.im,
        im = re * other.im + im * other.re,
    )

    operator fun div(other: Double) = Complex(re / other, im / other)

//    override fun toString(): String = "${re.roundToInt()}+${im.roundToInt()}i"

    companion object {
        val UNITY = Complex(1.0)
        fun polar(modulus: Double, argument: Double) = Complex(cos(argument), sin(argument)) * modulus
    }
}

class FastFourierTransform {

    private val rev = IntArray(N)
    private val w = Array(N) { Complex.UNITY }

    init {
        val wn = Complex.polar(1.0, 2 * Math.PI / N)
        for (i in 1..<N) {
            w[i] = w[i - 1] * wn
            rev[i] = rev[i / 2] / 2
            if (i and 1 == 1) {
                rev[i] += N / 2
            }
        }
    }

    fun dft(p: Array<Complex>, inverse: Boolean = false) {
        prepare(p)
        var subProblemSize = 2
        while (subProblemSize <= N) {
            val wStep = (if (inverse) -1 else 1) * N / subProblemSize
            for (subProblemStart in 0..<N step subProblemSize) {
                var wIndex = 0
                for (j in subProblemStart..<subProblemStart + subProblemSize / 2) {
                    val u = p[j]
                    val t = w[wIndex.mod(N)] * p[j + subProblemSize / 2]
                    p[j] = u + t
                    p[j + subProblemSize / 2] = u - t
                    wIndex += wStep
                }
            }
            subProblemSize *= 2
        }
        if (inverse) for (j in 0..<N) p[j] = p[j] / N.toDouble()
    }

    private fun prepare(x: Array<Complex>) {
        for (i in 1..<N) {
            if (i < rev[i]) x[i] = x[rev[i]].also { x[rev[i]] = x[i] }
        }
    }

    companion object {
        const val LOG_N = 20
        const val N = 1 shl LOG_N
    }
}
