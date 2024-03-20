fun bitmaskOf(n: Int, action: (Int) -> Boolean): Int {
    var mask = 0
    for (i in 0 until n) if (action(i)) {
        mask = mask xorBit i
    }
    return mask
}

fun Int.forEachSubSet(action: (Int) -> Unit) {
    var subSet = this
    while (subSet > 0) {
        action(subSet)
        subSet = (subSet - 1) and this
    }
    action(0)
}
