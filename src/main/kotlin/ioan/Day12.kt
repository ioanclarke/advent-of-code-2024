package ioan

object Day12 : Day {

    data class Region(val cells: List<Cell<Char>>) {

        val area: Int get() = cells.size

        fun perimeter(grid: Grid<Char>): Int {
            return cells
                .flatMap { it.neighboursWithNulls(grid) }
                .filterNot { it?.value == cells.first().value }
                .count()
        }

        fun numberOfSides(): Int {
            return 0
        }

    }

    override fun part1(): Int {
        val grid = Grid.from(readLines(12, test = false))
        val regions = parseRegions(grid)
//        regions.forEach { println(it) }
        return regions.sumOf { it.perimeter(grid) * it.area }
    }

    override fun part2(): Int {
        return 0
    }

    private fun parseRegions(grid: Grid<Char>): List<Region> {

        val regions = mutableListOf<Region>()

        val cellsAlreadyInRegion = mutableSetOf<Cell<Char>>()

        for (cell in grid) {
//            println("Checking $cell")
            if (cell in cellsAlreadyInRegion) continue

            val region = mutableListOf<Cell<Char>>()
            region.add(cell)
            cellsAlreadyInRegion.add(cell)

            var neighbours = cell.neighbours(grid)

            while (true) {
                neighbours = neighbours.filter { it.value == cell.value }.filterNot { it in cellsAlreadyInRegion }
                if (neighbours.isEmpty()) {
                    regions.add(Region(region))
                    break
                }

                neighbours.forEach { region.add(it) }
                neighbours.forEach { cellsAlreadyInRegion.add(it) }
                neighbours = neighbours.flatMap { it.neighbours(grid) }.distinct()
            }
        }

        return regions
    }
}
