class Maze(private val maze: List<String>) {
    val n = maze.size
    val m = maze[0].length

    data class Position(
        val x: Int,
        val y: Int,
    ) {
        operator fun plus(movement: Movement) = Position(x + movement.deltaX, y + movement.deltaY)
    }

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

    fun Position.move(movement: Movement): Position? = (this + movement).takeIf { it.isValid && it.c != BLOCKER }

    fun Position.validMoves(movements: List<Movement> = Movements): List<Position> = movements.mapNotNull { move(it) }

    val positions = (0 until n * m).map { it.toPosition() }

    fun find(c: Char): List<Position> = positions.filter { it.c == c }

    operator fun IntArray.get(position: Position) = this[position.id]
    operator fun IntArray.set(position: Position, value: Int) {
        this[position.id] = value
    }

    companion object {
        val Movements = listOf(
            Movement(-1, 0),
            Movement(0, 1),
            Movement(1, 0),
            Movement(0, -1),
        )

        val Movements8 = Movements + listOf(
            Movement(-1, -1),
            Movement(-1, 1),
            Movement(1, -1),
            Movement(1, 1),
        )

        const val BLOCKER = '#'

        fun read(n: Int) = Maze(readStrings(n))
    }
}

fun Maze.bfs(start: Maze.Position): IntArray {
    val distances = IntArray(n * m) { -1 }
    distances[start] = 0
    val queue = ArrayDeque(listOf(start))
    while (queue.isNotEmpty()) {
        val u = queue.removeFirst()
        for (v in u.validMoves()) if (distances[v] == -1) {
            distances[v] = distances[u] + 1
            queue += v
        }
    }
    return distances
}

fun Maze.dijkstra(start: Maze.Position, color: Char): IntArray {
    val distances = IntArray(n * m) { -1 }
    val q = PriorityQueue<Pair<Maze.Position, Int>>(compareBy { it.second })
    q += start to 0
    while (q.isNotEmpty()) {
        val (u, distanceU) = q.poll()
        if (distances[u] != -1) continue
        distances[u] = distanceU
        for (v in u.validMoves()) {
            q += v to distances[u] + if (v.c != color) 1 else 0
        }
    }
    return distances
}

fun Maze.stronglyConnectedComponents(movements: List<Maze.Movement> = Maze.Movements): List<Int> {
    val componentId = IntArray(n * m) { -1 }
    val componentSizes = mutableListOf<Int>()
    for (position in positions) if (componentId[position] == -1 && position.c != Maze.BLOCKER) {
        componentId[position] = componentSizes.size
        var componentSize = 1
        val queue = ArrayDeque(listOf(position))
        while (queue.isNotEmpty()) {
            val u = queue.removeFirst()
            for (v in u.validMoves(movements)) if (componentId[v] == -1) {
                componentId[v] = componentId[u]
                componentSize++
                queue += v
            }
        }
        componentSizes += componentSize
    }
    return componentSizes
}
