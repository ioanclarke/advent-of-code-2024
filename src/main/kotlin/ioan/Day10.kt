package ioan

object Day10 : Day {

    override fun part1(): Int = solve(distinctNeighbours = true)

    override fun part2(): Int = solve(distinctNeighbours = false)

    private fun solve(distinctNeighbours: Boolean): Int {
        val grid = Grid(readLines(10).map { it.toDigits() })
        return grid
            .filter { it.value == 0 }
            .sumOf { cell ->
                (1..9).fold(listOf(cell)) { neighbours, height ->
                    neighbours
                        .flatMap {
                            listOfNotNull(
                                grid.at(it.x, it.y - 1),
                                grid.at(it.x + 1, it.y),
                                grid.at(it.x, it.y + 1),
                                grid.at(it.x - 1, it.y)
                            )
                        }
                        .filter { it.value == height }
                        .let { if (distinctNeighbours) it.distinct() else it }
                }.size
            }
    }
}
