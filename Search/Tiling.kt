class Tiling(private val h: Int, private val w: Int) {
    private val tiles = mutableListOf<Pii>()

    /** Tile can be rotated. */
    fun addTile(a: Int, b: Int) {
        tiles += a to b
    }

    fun pave(): Boolean = dfs(BitSet().apply { set(0, h * w) }, BitSet().apply { set(0, tiles.size) })

    private fun tryPave(ground: BitSet, x: Int, y: Int, a: Int, b: Int): BitSet? {
        if (x + a > h || y + b > w) return null
        val newGround = ground.clone() as BitSet
        for (i in 0 until a) {
            for (j in 0 until b) {
                val p = (x + i) * w + y + j
                if (!newGround[p]) return null
                newGround.clear(p)
            }
        }
        return newGround
    }

    private fun dfs(ground: BitSet, tileSet: BitSet): Boolean {
        val cell = ground.nextSetBit(0)
        if (cell == -1) return true
        for (i in tileSet.stream()) {
            val newTileSet = tileSet.clone() as BitSet
            newTileSet.clear(i)
            val t = tiles[i]
            tryPave(ground, cell / w, cell % w, t.first, t.second)?.let {
                if (dfs(it, newTileSet)) return true
            }
            if (t.second != t.first) {
                tryPave(ground, cell / w, cell % w, t.second, t.first)?.let {
                    if (dfs(it, newTileSet)) return true
                }
            }
        }
        return false
    }
}
