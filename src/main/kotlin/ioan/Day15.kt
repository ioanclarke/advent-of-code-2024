package ioan

object Day15 : Day {

    private const val ROBOT = '@'
    private const val BOX = 'O'
    private const val WALL = '#'
    private const val EMPTY = '.'

    private enum class Direction(val xDiff: Int, val yDiff: Int) {
        UP(0, -1),
        RIGHT(1, 0),
        DOWN(0, 1),
        LEFT(-1, 0);

        companion object {
            fun fromString(s: Char): Direction = when (s) {
                '^' -> UP
                '>' -> RIGHT
                'v' -> DOWN
                '<' -> LEFT
                else -> error("unreachable")
            }
        }
    }

    override fun part1(): Int {
        val (gridRaw, movesRaw) = readText(15).split("\n\n")
        val initialGrid = Grid.from(gridRaw.split("\n"))
        val initialRobotPosition: Cell<Char> = initialGrid.find(ROBOT)!!
        val moves = movesRaw.trim().toList().filterNot { it == '\n' }.map { Direction.fromString(it) }
        val (_, grid) = moves.fold(initialRobotPosition to initialGrid) { (prevRobPos, prevGrid), direction ->
            tryToMove(
                prevRobPos,
                prevGrid,
                direction
            )
        }
        return grid
            .filter { it.value == BOX }
            .sumOf { it.coordinate }
    }

    private fun tryToMove(
        robotPosition: Cell<Char>,
        grid: Grid<Char>,
        direction: Direction
    ): Pair<Cell<Char>, Grid<Char>> {
        val tileAbove = grid.at(robotPosition.x + direction.xDiff, robotPosition.y + direction.yDiff)!!
        if (tileAbove.value == WALL)
            return robotPosition to grid

        val robotAbove = Cell(ROBOT, tileAbove.x, tileAbove.y)
        val emptyInPrevRobotPos = Cell(EMPTY, robotPosition.x, robotPosition.y)

        if (tileAbove.value == EMPTY)
            return robotAbove to grid.with(
                robotAbove,
                emptyInPrevRobotPos
            )

        val tilesAboveUntilWall = tilesUntilWall(robotPosition, grid, direction)

        if (tilesAboveUntilWall.all { it.value == BOX })
            return robotPosition to grid

        val tilesToPush = tilesAboveUntilWall.takeWhile {
            it.value == BOX && grid.valueAt(it.x + direction.xDiff, it.y + direction.yDiff).isEmptyOrBox()
        }

        return if (tilesToPush.isNotEmpty())
            robotAbove to grid.with(tilesToPush.map {
                Cell(
                    it.value,
                    it.x + direction.xDiff,
                    it.y + direction.yDiff
                )
            } + listOf(
                robotAbove,
                emptyInPrevRobotPos
            ))
        else
            robotPosition to grid
    }

    private fun tilesUntilWall(robotPosition: Cell<Char>, grid: Grid<Char>, direction: Direction): List<Cell<Char>> {
        val range = when (direction) {
            Direction.UP -> robotPosition.y - 1 downTo 1
            Direction.RIGHT -> robotPosition.x + 1..grid.width
            Direction.DOWN -> robotPosition.y + 1..grid.height
            Direction.LEFT -> robotPosition.x - 1 downTo 1
        }

        return range
            .map { if (direction.xDiff == 0) grid.at(robotPosition.x, it)!! else grid.at(it, robotPosition.y)!! }
            .takeWhile { it.value != WALL }
    }

    private val Cell<Char>.coordinate: Int get() = (this.x - 1) + 100 * (this.y - 1)

    private fun Char.isEmptyOrBox() = this == EMPTY || this == BOX

    override fun part2(): Int {
        return 0
    }
}
