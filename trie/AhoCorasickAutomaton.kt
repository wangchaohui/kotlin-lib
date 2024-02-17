class AhoCorasickAutomaton(private val dictionary: List<String>) {

    private class TrieNode {
        val wordIds = mutableListOf<Int>()
        val children: Array<TrieNode?> = Array(26) { null }
        private var dictionarySuffixNode = this
        var suffixNode: TrieNode? = null
            set(value) {
                field = checkNotNull(value)
                dictionarySuffixNode =
                    if (value.wordIds.isEmpty()) value.dictionarySuffixNode else value
                dictionarySuffixNode.dictionaryChildren += this
            }
        val dictionaryChildren = mutableListOf<TrieNode>()

        operator fun get(c: Char) = children[c - 'a']
        fun build(c: Char) = this[c] ?: TrieNode().also { children[c - 'a'] = it }
        fun suffixAddChar(i: Int) = suffixNode?.let { it.children[i] }
        var matchCount = 0
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
                    child.suffixNode = node.suffixAddChar(i) ?: trie
                    queue += child
                } else {
                    node.children[i] = node.suffixAddChar(i)
                }
            }
        }
    }

    fun query(s: String): IntArray {
        var node = trie
        for (c in s) {
            node = node[c] ?: trie
            node.matchCount++
        }
        return collectMatchCount()
    }

    private fun collectMatchCount(): IntArray {
        val ans = IntArray(dictionary.size)
        fun TrieNode.dfs(): Int {
            for (child in dictionaryChildren) matchCount += child.dfs()
            for (wordId in wordIds) ans[wordId] += matchCount
            return matchCount.also { matchCount = 0 }
        }
        trie.dfs()
        return ans
    }
}
