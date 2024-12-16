package ioan

import ioan.Day10.ScoreOrRating.RATING
import ioan.Day10.ScoreOrRating.SCORE

object Day10 : Day {

    enum class ScoreOrRating(val requiresDistinctNeighbours: Boolean) {
        SCORE(true),
        RATING(false)
    }

    override fun part1(): Int {
        val grid = Grid(readLines(10).map { it.toDigits() })
        return grid
            .filter { it.value == 0 }
            .sumOf { find(SCORE, it, grid) }
    }

    override fun part2(): Int {
        val grid = Grid(readLines(10).map { it.toDigits() })
        return grid
            .filter { it.value == 0 }
            .sumOf { find(RATING, it, grid) }
    }

    private fun find(type: ScoreOrRating, cell: Cell<Int>, grid: Grid<Int>): Int =
        (1..9).fold(listOf(cell)) { acc, height ->
            getNeighbours(acc, grid, height, distinct = type.requiresDistinctNeighbours)
        }.size

    private fun getNeighbours(cells: List<Cell<Int>>, grid: Grid<Int>, value: Int, distinct: Boolean): List<Cell<Int>> {
        val neighbours = cells.map { it.neighbours(grid) }.flatten().filter { it.value == value }
        return if (distinct) neighbours.distinct() else neighbours
    }

    private fun Cell<Int>.neighbours(grid: Grid<Int>): List<Cell<Int>> = listOfNotNull(
        grid.at(this.x, this.y - 1),
        grid.at(this.x + 1, this.y),
        grid.at(this.x, this.y + 1),
        grid.at(this.x - 1, this.y)
    )
}
