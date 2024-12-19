package ioan

object Day15 : Day {

    private const val UP = '^'
    private const val RIGHT = '>'
    private const val DOWN = 'v'
    private const val LEFT = '<'

    private const val ROBOT = '@'
    private const val BOX = 'O'
    private const val WALL = '#'
    private const val EMPTY = '.'

    private fun Char.isEmptyOrBox() = this == EMPTY || this == BOX

    private enum class Direction(val xDiff: Int, val yDiff: Int) {
        UP(0, -1),
        RIGHT(1, 0),
        DOWN(0, 1),
        LEFT(-1, 0);

//        companion object {
//            fun fromString(s: String): Direction = when(s) {
//
//            }
//        }
    }


    private fun tilesUntilWall(robotPosition: Cell<Char>, grid: Grid<Char>, direction: Direction): List<Cell<Char>> {
        val range = when (direction) {
            Direction.UP -> (robotPosition.y - 1 downTo 1)
            Direction.RIGHT -> robotPosition.x + 1..grid.width
            Direction.DOWN -> robotPosition.y + 1..grid.height
            Direction.LEFT -> robotPosition.x - 1 downTo 1
        }

        return range
            .map { if (direction.xDiff == 0) grid.at(robotPosition.x, it)!! else grid.at(it, robotPosition.y)!! }
            .takeWhile { it.value != WALL }
    }


//    private fun tryToMove(robotPosition: Cell<Char>, grid: Grid<Char>, direction: Direction): Pair<Cell<Char>, Grid<Char>> {
//        val tileAbove = grid.at(robotPosition.x + direction.xDiff, robotPosition.y + direction.yDiff)!!
//        if (tileAbove.value == WALL)
//            return robotPosition to grid
//
//        val robotAbove = Cell(ROBOT, tileAbove.x, tileAbove.y)
//        val emptyInPrevRobotPos = Cell(EMPTY, robotPosition.x, robotPosition.y)
//
//        if (tileAbove.value == EMPTY)
//            return robotAbove to grid.with(
//                robotAbove,
//                emptyInPrevRobotPos
//            )
//
//        val tilesAboveUntilWall = tilesUntilWall(robotPosition, grid, Direction.UP)
//
//        if (tilesAboveUntilWall.all { it.value == BOX })
//            return robotPosition to grid
//
//        // Therefore there is space to move up, so work out which boxes to push
//        val tilesToPush = tilesAboveUntilWall.takeWhile {
//            it.value == BOX && grid.valueAt(it.x + direction.xDiff, it.y + direction.yDiff).isEmptyOrBox()
//        }
//
//        return if (tilesToPush.isNotEmpty())
//            robotAbove to grid.with(tilesToPush.map { Cell(it.value, it.x + direction.xDiff, it.y + direction.yDiff) } + listOf(
//                robotAbove,
//                emptyInPrevRobotPos
//            ))
//        else
//            robotPosition to grid
//    }

    private fun tryToMoveUp(robotPosition: Cell<Char>, grid: Grid<Char>): Pair<Cell<Char>, Grid<Char>> {
        val tileAbove = grid.at(robotPosition.x, robotPosition.y - 1)!!
        if (tileAbove.value == WALL)
            return robotPosition to grid

        val robotAbove = Cell(ROBOT, tileAbove.x, tileAbove.y)
        val emptyInPrevRobotPos = Cell(EMPTY, robotPosition.x, robotPosition.y)

        if (tileAbove.value == EMPTY)
            return robotAbove to grid.with(
                robotAbove,
                emptyInPrevRobotPos
            )

        val tilesAboveUntilWall = (robotPosition.y - 1 downTo 1)
            .map { grid.at(robotPosition.x, it)!! }
            .takeWhile { it.value != WALL }

        if (tilesAboveUntilWall.all { it.value == BOX })
            return robotPosition to grid

        // Therefore there is space to move up, so work out which boxes to push
        val tilesToPush = tilesAboveUntilWall.takeWhile {
            it.value == BOX && grid.valueAt(it.x, it.y - 1).isEmptyOrBox()
        }

        return if (tilesToPush.isNotEmpty())
            robotAbove to grid.with(tilesToPush.map { Cell(it.value, it.x, it.y - 1) } + listOf(
                robotAbove,
                emptyInPrevRobotPos
            ))
        else
            robotPosition to grid
    }

    private fun tryToMoveRight(robotPosition: Cell<Char>, grid: Grid<Char>): Pair<Cell<Char>, Grid<Char>> {
        val tileAbove = grid.at(robotPosition.x + 1, robotPosition.y)!!
        if (tileAbove.value == WALL)
            return robotPosition to grid

        val robotAbove = Cell(ROBOT, tileAbove.x, tileAbove.y)
        val emptyInPrevRobotPos = Cell(EMPTY, robotPosition.x, robotPosition.y)

        if (tileAbove.value == EMPTY)
            return robotAbove to grid.with(
                robotAbove,
                emptyInPrevRobotPos
            )

        val tilesAboveUntilWall = (robotPosition.x + 1..grid.width)
            .map { grid.at(it, robotPosition.y)!! }
            .takeWhile { it.value != WALL }

        if (tilesAboveUntilWall.all { it.value == BOX })
            return robotPosition to grid

        // Therefore there is space to move up, so work out which boxes to push
        val tilesToPush = tilesAboveUntilWall.takeWhile {
            it.value == BOX && grid.valueAt(it.x + 1, it.y).isEmptyOrBox()
        }

        return if (tilesToPush.isNotEmpty())
            robotAbove to grid.with(tilesToPush.map { Cell(it.value, it.x + 1, it.y) } + listOf(
                robotAbove,
                emptyInPrevRobotPos
            ))
        else
            robotPosition to grid
    }

    private fun tryToMoveDown(robotPosition: Cell<Char>, grid: Grid<Char>, direction: Direction): Pair<Cell<Char>, Grid<Char>> {
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

        // Therefore there is space to move up, so work out which boxes to push
        val tilesToPush = tilesAboveUntilWall.takeWhile {
            it.value == BOX && grid.valueAt(it.x + direction.xDiff, it.y + direction.yDiff).isEmptyOrBox()
        }

        return if (tilesToPush.isNotEmpty())
            robotAbove to grid.with(tilesToPush.map { Cell(it.value, it.x + direction.xDiff, it.y + direction.yDiff) } + listOf(
                robotAbove,
                emptyInPrevRobotPos
            ))
        else
            robotPosition to grid
    }

    private fun tryToMoveLeft(robotPosition: Cell<Char>, grid: Grid<Char>, direction: Direction): Pair<Cell<Char>, Grid<Char>> {
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

        // Therefore there is space to move up, so work out which boxes to push
        val tilesToPush = tilesAboveUntilWall.takeWhile {
            it.value == BOX && grid.valueAt(it.x + direction.xDiff, it.y + direction.yDiff).isEmptyOrBox()
        }

        return if (tilesToPush.isNotEmpty())
            robotAbove to grid.with(tilesToPush.map { Cell(it.value, it.x + direction.xDiff, it.y + direction.yDiff) } + listOf(
                robotAbove,
                emptyInPrevRobotPos
            ))
        else
            robotPosition to grid

    }

    override fun part1(): Int {
        val (gridRaw, movesRaw) = readText(15, false).split("\n\n")

        val grid = Grid.from(gridRaw.split("\n"))
        val moves = movesRaw.trim().toList().filterNot { it == '\n' }
        val robotPosition: Cell<Char> = grid.find(ROBOT)!!
        val (_, g) = moves.fold(robotPosition to grid) { (prevRobPos, prevGrid), move ->
            when (move) {
                UP -> {
//                    println("moving up")
                    val tryToMoveUp = tryToMoveUp(prevRobPos, prevGrid)
//                    println(tryToMoveUp.second)
                    tryToMoveUp
                }
                RIGHT -> {
                    val tryToMoveRight = tryToMoveRight(prevRobPos, prevGrid)
//                    println(tryToMoveRight.second)
                    tryToMoveRight
                }
                DOWN -> {
//                    println("moving down")
                    val tryToMoveDown = tryToMoveDown(prevRobPos, prevGrid, Direction.DOWN)
//                    println(tryToMoveDown.second)
                    tryToMoveDown
                }
                LEFT -> {
//                    println("moving left")
                    val tryToMoveLeft = tryToMoveLeft(prevRobPos, prevGrid, Direction.LEFT)
//                    println(tryToMoveLeft.second)
                    tryToMoveLeft
                }
                else -> {
                    throw IllegalStateException()
                }
            }
        }
        println(g)
        return g
            .filter { it.value == BOX }
            .sumOf { it.coordinate }
    }

    private val Cell<Char>.coordinate: Int get() = (this.x - 1) + 100 * (this.y - 1)

    override fun part2(): Int {
        return 0
    }
}
