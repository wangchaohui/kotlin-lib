class Maze(private val maze: List<String>) {
    val n = maze.size
    val m = maze[0].length

    data class Position(
        val x: Int,
        val y: Int,
    )

    data class Movement(
        val deltaX: Int,
        val deltaY: Int,
    )

    fun Int.toPosition() = Position(this / m, this % m)

    val Position.c: Char
        get() = maze[x][y]

    val Position.id: Int
        get() = x * m + y

    val Position.isValid: Boolean
        get() = x in 0 until n && y in 0 until m

    fun Position.move(movement: Movement): Position? =
        Position(x + movement.deltaX, y + movement.deltaY).takeIf { it.isValid && it.c != BLOCKER }

    fun Position.validMoves(): List<Position> = Movements.mapNotNull { move(it) }

    private val positions = (0 until n * m).asSequence().map { it.toPosition() }

    fun find(c: Char): List<Position> = positions.filter { it.c == c }.toList()

    companion object {
        val Movements = listOf(
            Movement(-1, 0),
            Movement(0, 1),
            Movement(1, 0),
            Movement(0, -1),
        )

        const val BLOCKER = '#'
    }
}

fun Maze.bfs(start: Maze.Position): IntArray {
    val distances = IntArray(n * m) { -1 }
    distances[start.id] = 0
    val queue = ArrayDeque(listOf(start))
    while (queue.isNotEmpty()) {
        val u = queue.removeFirst()
        for (v in u.validMoves()) if (distances[v.id] == -1) {
            distances[v.id] = distances[u.id] + 1
            queue += v
        }
    }
    return distances
}
