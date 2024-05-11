class Trie {
    private class TrieNode {
        var count = 0
        val children: Array<TrieNode?> = Array(26) { null }

        operator fun get(c: Char) = children[c - 'a']
        fun build(c: Char) = this[c] ?: TrieNode().also { children[c - 'a'] = it }
    }

    private val trie = TrieNode()

    fun addWord(word: String) {
        var node = trie
        for (c in word) node = node.build(c)
        node.count++
    }
}
