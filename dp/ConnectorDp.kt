class ConnectorDp(private val matrix: Array<BooleanArray>) {
    @JvmInline
    private value class State(val value: Int) {

        operator fun get(index: Int): Int = (value / BasePower[index]).mod(BASE)

        fun set(index: Int, v: Int) = State(value + (v - get(index)) * BasePower[index])

        fun shiftLeft(maxSize: Int): State? = if (value < BasePower[maxSize]) State(value * BASE) else null

        override fun toString() = (0..SIZE).joinToString("") { this[it].toString() }

        private companion object {
            const val SIZE = 12
            const val BASE = 3
            val BasePower = IntArray(SIZE + 1).apply {
                this[0] = 1
                for (i in 1..SIZE) {
                    this[i] = this[i - 1] * BASE
                }
            }
        }
    }

    private val n = matrix.size
    private val m = matrix[0].size

    fun solve(): Long {
        var previous = mapOf(State(0) to 1L)
        var ans = 0L
        for (i in 0..<n) {
            val newLine = mutableMapOf<State, Long>()
            for ((key, value) in previous) {
                key.shiftLeft(m)?.let { newLine.increase(it, value) }
            }
            previous = newLine
            for (j in 0..<m) {
                val now = mutableMapOf<State, Long>()
                var nowAns = 0L
                for ((key, value) in previous) {
                    val c0 = key[j]
                    val c1 = key[j + 1]
                    if (matrix[i][j]) when {
                        c0 == 0 && c1 == 0 -> now.increase(key.set(j, 1).set(j + 1, 2), value)
                        c0 == 0 -> {
                            now.increase(key, value)
                            now.increase(key.set(j, c1).set(j + 1, 0), value)
                        }

                        c1 == 0 -> {
                            now.increase(key, value)
                            now.increase(key.set(j, 0).set(j + 1, c0), value)
                        }

                        c0 == 2 && c1 == 1 -> now.increase(key.set(j, 0).set(j + 1, 0), value)
                        c0 == c1 -> {
                            var c = 0
                            val k = (if (c0 == 1) j + 1..m else j downTo 0).first {
                                when (key[it]) {
                                    1 -> c++
                                    2 -> c--
                                }
                                c == 0
                            }
                            now.increase(key.set(j, 0).set(j + 1, 0).set(k, c0), value)
                        }

                        key.set(j, 0).set(j + 1, 0).value == 0 -> {
                            nowAns += value
                        }
                    } else if (c0 == 0 && c1 == 0) now.increase(key, value)
                }
                if (matrix[i][j]) {
                    ans = nowAns
                }
                previous = now
            }
        }
        return ans
    }

    private companion object {
        fun MutableMap<State, Long>.increase(key: State, value: Long) {
            this[key] = getOrElse(key) { 0L } + value
        }
    }
}
