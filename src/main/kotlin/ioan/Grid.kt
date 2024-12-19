package ioan

data class Grid<T>(private val cells: List<List<T>>) : Iterable<Cell<T>> {

    override fun toString(): String {
        return cells.joinToString("\n") { it.joinToString(" ") }
    }

    fun at(x: Int, y: Int): Cell<T>? {
        val value = cells.getOrNull(y - 1)?.getOrNull(x - 1)
        return if (value == null) null else Cell(value, x, y)
    }

    fun valueAt(x: Int, y: Int): T = cells[y - 1][x - 1]

    fun with(vararg newCells: Cell<T>): Grid<T> {
        val mutableGrid: MutableList<MutableList<T>> = cells.map { it.toMutableList() }.toMutableList()
        newCells.forEach { mutableGrid[it.y - 1][it.x - 1] = it.value }
        return Grid(mutableGrid)
    }

    fun with(newCells: List<Cell<T>>): Grid<T> {
        val mutableGrid: MutableList<MutableList<T>> = cells.map { it.toMutableList() }.toMutableList()
        newCells.forEach { mutableGrid[it.y - 1][it.x - 1] = it.value }
        return Grid(mutableGrid)
    }

    fun find(value: T): Cell<T>? = this.find { it.value == value }

    val width: Int get() = cells.first().size

    val height: Int get() = cells.size

    override fun iterator(): Iterator<Cell<T>> {
        val ret = mutableListOf<Cell<T>>()

        cells.forEachIndexed { y, row -> row.forEachIndexed { x, cellValue -> ret.add(Cell(cellValue, x + 1, y + 1)) } }
        return ret.iterator()
    }

    companion object {
        fun from(lines: List<String>): Grid<Char> {
            val cells = lines.map { it.toList() }
            return Grid(cells)
        }
    }
}


data class Cell<T>(val value: T, val x: Int, val y: Int) {
    fun toPoint(): Point = Point(x, y)

    fun neighbours(grid: Grid<T>): List<Cell<T>> =
        listOfNotNull(
            grid.at(this.x, this.y - 1),
            grid.at(this.x + 1, this.y),
            grid.at(this.x, this.y + 1),
            grid.at(this.x - 1, this.y)
        )

    fun neighboursWithNulls(grid: Grid<T>): List<Cell<T>?> =
        listOf(
            grid.at(this.x, this.y - 1),
            grid.at(this.x + 1, this.y),
            grid.at(this.x, this.y + 1),
            grid.at(this.x - 1, this.y)
        )

}

data class Point(val x: Int, val y: Int)
