interface StringHash<T> {
    fun subHash(startIndex: Int, endIndex: Int): T
}

interface StringHashAlgorithm<T> {
    fun hash(s: String): StringHash<T>
}

class StringHashAlgorithmImpl(
    private val base: Int,
    private val module: Int,
) : StringHashAlgorithm<Int> {
    private val bases = mutableListOf(1)

    inner class StringHashImpl(private val hashes: IntArray) : StringHash<Int> {
        override fun subHash(startIndex: Int, endIndex: Int): Int =
            (hashes[endIndex] - hashes[startIndex].toLong() * bases[endIndex - startIndex])
                .mod(module)
    }

    override fun hash(s: String): StringHash<Int> {
        while (bases.size <= s.length) bases += (bases.last().toLong() * base).mod(module)
        val hashes = IntArray(s.length + 1)
        for (i in s.indices) {
            hashes[i + 1] = (hashes[i].toLong() * base + (s[i] - 'a')).mod(module)
        }
        return StringHashImpl(hashes)
    }
}

object CombinedStringHashAlgorithm : StringHashAlgorithm<List<Int>> {
    class StringHashImpl(private val list: List<StringHash<Int>>) : StringHash<List<Int>> {
        override fun subHash(startIndex: Int, endIndex: Int) =
            list.map { it.subHash(startIndex, endIndex) }
    }

    override fun hash(s: String) = StringHashImpl(HashAlgorithms.map { it.hash(s) })

    private val HashAlgorithms = listOf(
        StringHashAlgorithmImpl(29, 1000000007),
        StringHashAlgorithmImpl(31, 998244353),
    )
}
