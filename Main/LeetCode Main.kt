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
infix fun Long.hasBit(i: Int): Boolean = this and (1L shl i) > 0
infix fun Long.xorBit(i: Int): Long = this xor (1L shl i)

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

fun String.checkRemoveSurrounding(prefix: CharSequence, suffix: CharSequence = prefix): String {
    assert(startsWith(prefix) && endsWith(prefix))
    return removeSurrounding(prefix, suffix)
}

fun readString(): String = read().checkRemoveSurrounding("\"")
fun readArray(): String = read().checkRemoveSurrounding("[", "]")

fun String.toIntArray(): IntArray = split(',').map(String::toInt).toIntArray()
fun readIntArray(): IntArray = readArray().toIntArray()
fun readArrayIntArray(): Array<IntArray> = readArray()
    .checkRemoveSurrounding("[", "]")
    .split(Regex("""],\["""))
    .map(String::toIntArray)
    .toTypedArray()

fun println(a: IntArray) = println(a.contentToString())
fun println(a: BooleanArray) = println(a.contentToString())

fun solve() {
    println(Solution().findAnswer(readInt(), readArrayIntArray()))
}

fun main() {
    repeat(readInt()) { solve() }
//    solve()
}
