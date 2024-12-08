package ioan

object Day6 : Day {

    private const val OBSTRUCTION = '#'

    private enum class Direction(val xDiff: Int, val yDiff: Int) {
        UP(0, -1),
        DOWN(0, 1),
        LEFT(-1, 0),
        RIGHT(1, 0);

        fun turnRight90Degress(): Direction = when(this) {
            UP -> RIGHT
            RIGHT -> DOWN
            DOWN -> LEFT
            LEFT -> UP
        }
    }

    override fun part1(): Int {
        val grid = Grid.from(readLines(6))

        var currentPosition = getStartingCell(grid)
        var currentDirection = Direction.UP

        val positionsVisited = mutableSetOf<Cell<Char>>()

        while (true) {
            positionsVisited.add(currentPosition)
            val nextPosition = grid.at(
                currentPosition.x + currentDirection.xDiff,
                currentPosition.y + currentDirection.yDiff
            )

            if (nextPosition == null) {
                break
            }

            if (nextPosition.value == OBSTRUCTION) {
                currentDirection = currentDirection.turnRight90Degress()
            } else {
                currentPosition = nextPosition
            }
        }

        return positionsVisited.size
    }


    private fun getStartingCell(grid: Grid<Char>): Cell<Char> {
        return grid.find { it.value == '^' }!!
    }

    override fun part2(): Int {
//        TODO()
        /*
        loop over input:
            replace each non-obstacle and non-starting position with an obstacle
            keep track each two-step sequence the guard takes e.g ((x=1,y=1),(x=2,y=1))
            if any two-step sequence happens more than once, the guard must be stuck in a loop
         */

        val grid = Grid.from(readLines(6, test = true))
        println(grid)

        val startingPosition = getStartingCell(grid)

        var currentPosition = startingPosition

        var currentDirection = Direction.UP

        val positionsVisited = mutableSetOf<Cell<Char>>()

        while (true) {
            println(currentPosition)
            positionsVisited.add(currentPosition)
            val nextPosition = grid.at(
                currentPosition.x + currentDirection.xDiff,
                currentPosition.y + currentDirection.yDiff
            )

            if (nextPosition == null) {
                break
            }

            if (nextPosition.value == OBSTRUCTION) {
                currentDirection = currentDirection.turnRight90Degress()
            } else {
                currentPosition = nextPosition
            }
        }

        return positionsVisited.size
    }
}
