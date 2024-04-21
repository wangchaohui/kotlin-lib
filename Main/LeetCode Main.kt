import MutableIntMap.Companion.mutableIntMapOf
import java.util.*
import kotlin.collections.*
import kotlin.math.*

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

infix fun Int.hasBit(i: Int): Boolean = this and (1 shl i) > 0
infix fun Int.xorBit(i: Int): Int = this xor (1 shl i)

const val INT_HALF_MAX_VALUE: Int = Int.MAX_VALUE / 2

// #################################################################################################

class Solution {

}

// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

val _reader = System.`in`.bufferedReader()

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

fun String.toIntArray(): IntArray = split(',').map(String::toInt).toIntArray()

fun readArray(): String {
    val a = read()
    assert(a.startsWith('[') && a.endsWith(']'))
    return a.removeSurrounding("[", "]")
}

fun readIntArray(): IntArray = readArray().toIntArray()

fun readArrayIntArray(): Array<IntArray> =
    readArray()
        .removeSurrounding("[", "]")
        .split(Regex("""],\["""))
        .map(String::toIntArray)
        .toTypedArray()

fun println(a: BooleanArray) {
    println(a.joinToString(separator = ",", prefix = "[", postfix = "]"))
}

fun solve() {
    println(Solution().findAnswer(readInt(), readArrayIntArray()))
}

fun main() {
    repeat(readInt()) { solve() }
//    solve()
}
