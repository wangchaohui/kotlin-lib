import java.io.*
import java.util.*
import kotlin.collections.*
import kotlin.math.*

val INPUT: InputStream = System.`in`
val OUTPUT: PrintStream = System.out

val _reader = INPUT.bufferedReader()

var _tokenizer: StringTokenizer = StringTokenizer("")
fun read(): String {
    while (!_tokenizer.hasMoreTokens()) {
        _tokenizer = StringTokenizer(_reader.readLine() ?: return "", " ")
    }
    return _tokenizer.nextToken()
}

fun readInt() = read().toInt()
fun readDouble() = read().toDouble()
fun readLong() = read().toLong()
fun readStrings(n: Int) = List(n) { read() }
fun readInts(n: Int) = List(n) { readInt() }
fun readIntArray(n: Int) = IntArray(n) { readInt() }
fun readDoubles(n: Int) = List(n) { readDouble() }
fun readDoubleArray(n: Int) = DoubleArray(n) { readDouble() }
fun readLongs(n: Int) = List(n) { readLong() }
fun readLongArray(n: Int) = LongArray(n) { readLong() }

val _writer = PrintWriter(OUTPUT, false)
inline fun output(block: PrintWriter.() -> Unit) {
    _writer.apply(block).flush()
}

class MutableIntMap<K> : LinkedHashMap<K, Int>() {
    override operator fun get(key: K): Int = super.get(key) ?: 0

    fun increment(key: K, value: Int = 1) {
        this[key] = this[key] + value
    }

    companion object {
        fun <K> mutableIntMapOf(vararg pairs: Pair<K, Int>) =
            MutableIntMap<K>().apply { putAll(pairs) }
    }
}

typealias Pii = Pair<Int, Int>
fun readPii() = readInt() to readInt()
fun readPiis(n: Int) = List(n) { readPii() }

infix fun Int.hasBit(i: Int): Boolean = this and (1 shl i) > 0
infix fun Int.xorBit(i: Int): Int = this xor (1 shl i)

fun largerStackSize(stackSizeMegaBytes: Int = 100, action: () -> Unit) {
    Thread(null, action, "", 1024L * 1024 * stackSizeMegaBytes).apply {
        start()
        join()
    }
}

// #################################################################################################

fun solve() {
    val n = readInt()
}

fun main() {
//    repeat(readInt()) { solve() }
    solve()
}
