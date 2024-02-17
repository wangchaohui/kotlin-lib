class AhoCorasickAutomaton(private val dictionary: List<String>) {

    private class TrieNode {
        val wordIds = mutableListOf<Int>()
        val children: Array<TrieNode?> = Array(26) { null }
        var suffixNode: TrieNode? = null
            set(value) {
                field = checkNotNull(value)
                dictionarySuffixNode =
                    if (value.wordIds.isEmpty()) value.dictionarySuffixNode else value
            }
        private var dictionarySuffixNode: TrieNode? = null
        fun matchIds() = generateSequence(this) { it.dictionarySuffixNode }.flatMap { it.wordIds }
        operator fun get(c: Char) = children[c - 'a']
        fun build(c: Char) = this[c] ?: TrieNode().also { children[c - 'a'] = it }
    }

    private val trie = TrieNode()

    init {
        dictionary.forEachIndexed(::addWord)
        buildSuffix()
    }

    private fun addWord(id: Int, word: String) {
        var node = trie
        for (c in word) node = node.build(c)
        node.wordIds += id
    }

    private fun buildSuffix() {
        val queue = ArrayDeque(listOf(trie))
        while (queue.isNotEmpty()) {
            val node = queue.removeFirst()
            for ((i, child) in node.children.withIndex()) {
                if (child != null) {
                    child.suffixNode = node.suffixNode?.let { it.children[i] } ?: trie
                    queue += child
                } else {
                    node.suffixNode?.let { node.children[i] = it.children[i] }
                }
            }
        }
    }

    fun query(s: String): IntArray {
        val ans = IntArray(dictionary.size)
        var node = trie
        for (c in s) {
            node = node[c] ?: trie
            for (id in node.matchIds()) ans[id]++
        }
        return ans
    }
}
