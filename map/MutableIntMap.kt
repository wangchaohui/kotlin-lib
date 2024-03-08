class MutableIntMap<K> : LinkedHashMap<K, Int>() {
    fun add(key: K, value: Int) {
        this[key] = getOrElse(key) { 0 } + value
    }

    fun increment(key: K) {
        add(key, 1)
    }
}
