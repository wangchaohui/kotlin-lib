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
