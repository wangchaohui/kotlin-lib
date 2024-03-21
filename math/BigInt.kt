data class Complex(
    val re: Double = 0.0,
    val im: Double = 0.0,
) {
    operator fun plus(other: Complex) = Complex(re + other.re, im + other.im)
    operator fun plus(other: Long) = Complex(re + other, im)
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
        for (i in 1 until N) {
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
            for (subProblemStart in 0 until N step subProblemSize) {
                var wIndex = 0
                for (j in subProblemStart until subProblemStart + subProblemSize / 2) {
                    val u = p[j]
                    val t = w[wIndex.mod(N)] * p[j + subProblemSize / 2]
                    p[j] = u + t
                    p[j + subProblemSize / 2] = u - t
                    wIndex += wStep
                }
            }
            subProblemSize *= 2
        }
        if (inverse) for (j in 0 until N) p[j] = p[j] / N.toDouble()
    }

    private fun prepare(x: Array<Complex>) {
        for (i in 1 until N) {
            if (i < rev[i]) x[i] = x[rev[i]].also { x[rev[i]] = x[i] }
        }
    }

    companion object {
        const val LOG_N = 19
        const val N = 1 shl LOG_N
    }
}

class BigInt(s: String) {
    private val p = Array(N) { Complex() }

    init {
        s.reversed().chunked(GROUP_SIZE).forEachIndexed { i, c ->
            p[i] = Complex(c.reversed().toDouble())
        }
    }

    override fun toString(): String {
        var first = true
        return p.reversed().joinToString("") { (re) ->
            val r = re.roundToInt()
            if (first && r == 0) return@joinToString ""
            val i = r.toString()
            if (first) {
                first = false
                i
            } else {
                i.padStart(GROUP_SIZE, '0')
            }
        }
    }

    private companion object {
        const val GROUP_SIZE = 1
        const val GROUP_MAX = 10
    }

    operator fun times(other: BigInt): BigInt {
        val fft = FastFourierTransform()
        fft.dft(p)
        fft.dft(other.p)
        for (i in p.indices) p[i] *= other.p[i]
        fft.dft(p, true)
        for (i in 0 until N - 1) {
            val a = p[i].re.roundToLong()
            p[i + 1] = p[i + 1] + a / GROUP_MAX
            p[i] = Complex((a % GROUP_MAX).toDouble())
        }
        return this
    }
}
