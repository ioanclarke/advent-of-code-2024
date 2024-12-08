package ioan


object Day4 : Day {

    enum class Movements(val x: Triple<Int, Int, Int>, val y: Triple<Int, Int, Int>) {
        NORTH(
            Triple(0, 0, 0), Triple(-1, -2, -3)
        ),
        NORTHEAST(
            Triple(1, 2, 3), Triple(-1, -2, -3)
        ),
        EAST(
            Triple(1, 2, 3), Triple(0, 0, 0)
        ),
        SOUTHEAST(
            Triple(1, 2, 3), Triple(1, 2, 3)
        ),
        SOUTH(
            Triple(0, 0, 0), Triple(1, 2, 3)
        ),
        SOUTHWEST(
            Triple(-1, -2, -3), Triple(1, 2, 3)
        ),
        WEST(
            Triple(-1, -2, -3), Triple(0, 0, 0)
        ),
        NORTHWEST(
            Triple(-1, -2, -3), Triple(-1, -2, -3)
        ),
    }

    override fun part1(): Int {
        val grid = buildGrid()

        fun spellsXmas(
            cell: Cell<Char>,
            movements: Movements
        ): Boolean =
            grid.at(cell.x + movements.x.first, cell.y + movements.y.first)?.value == 'M' &&
                    grid.at(cell.x + movements.x.second, cell.y + movements.y.second)?.value == 'A' &&
                    grid.at(cell.x + movements.x.third, cell.y + movements.y.third)?.value == 'S'


        return grid
            .filter { it.value == 'X' }
            .sumOf { cell -> Movements.entries.map { spellsXmas(cell, it) }.count { it } }
    }


    override fun part2(): Int {
        val grid = buildGrid()

        fun northWestToSouthEast(cell: Cell<Char>): Boolean {
            fun cellsEqual(northWest: Char, southEast: Char): Boolean =
                grid.at(cell.x - 1, cell.y - 1)?.value == northWest && grid.at(cell.x + 1, cell.y + 1)?.value == southEast

            return cellsEqual(northWest = 'M', southEast = 'S') || cellsEqual(northWest = 'S', southEast = 'M')
        }

        fun southWestToNorthEast(cell: Cell<Char>): Boolean {
            fun cellsEqual(northWest: Char, southEast: Char): Boolean =
                grid.at(cell.x - 1, cell.y + 1)?.value == northWest && grid.at(cell.x + 1, cell.y - 1)?.value == southEast

            return cellsEqual(northWest = 'M', southEast = 'S') || cellsEqual(northWest = 'S', southEast = 'M')
        }

        return grid.filter { it.value == 'A' }.count { northWestToSouthEast(it) && southWestToNorthEast(it) }
    }

    private fun buildGrid(): Grid<Char> {
        val lines: List<String> = readLines(4)
        val cells: List<List<Char>> = lines.map { it.toList() }
        return Grid(cells)
    }
}
