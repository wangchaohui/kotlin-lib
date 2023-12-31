fun nextPermutation(array: IntArray): Boolean {
    val i = (0..<array.lastIndex).lastOrNull { i -> array[i] < array[i + 1] } ?: return false
    val j = (i + 1..array.lastIndex).last { j -> array[i] < array[j] }
    array[i] = array[j].also { array[j] = array[i] }
    array.reverse(i + 1, array.size)
    return true
}
