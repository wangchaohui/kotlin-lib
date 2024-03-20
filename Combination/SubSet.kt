fun <T> List<T>.forEachSubSet(action: (List<T>) -> Unit) {
    for (q in 0 until (1 shl size)) {
        action(filterIndexed { i, _ -> q hasBit i })
    }
}
