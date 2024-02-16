class AhoCorasickAutomaton(private val dictionary: List<String>) {

    private class TrieNode(val parentLink: Pair<TrieNode, Char>?) {
        val wordIds = mutableListOf<Int>()
        val children: Array<TrieNode?> = Array(26) { null }
        var suffixNode: TrieNode? = null
        var dictionarySuffixNode: TrieNode? = null

        operator fun get(c: Char) = children[c - 'a']

        fun build(c: Char) = this[c] ?: TrieNode(this to c).also { children[c - 'a'] = it }

        operator fun contains(c: Char): Boolean = this[c] != null

        override fun toString() = parentLink?.let { (parent, c) -> "$parent$c" } ?: ""
    }

    private val trie = TrieNode(null)

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
            node.suffixNode = node.parentLink?.let { (parent, c) ->
                parent.suffixNode?.get(c) ?: trie
            }
            node.dictionarySuffixNode = generateSequence(node.suffixNode) { it.suffixNode }
                .firstOrNull { it.wordIds.isNotEmpty() }
            for ((i, child) in node.children.withIndex()) {
                if (child != null) {
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
            for (id in generateSequence(node) { it.dictionarySuffixNode }.flatMap { it.wordIds }) {
                ans[id]++
            }
        }
        return ans
    }
}
