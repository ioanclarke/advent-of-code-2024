package ioan

class Grid<out T>(private val cells: List<List<T>>) : Iterable<Cell<T>> {

    override fun toString(): String {
        return cells.joinToString("\n") { it.joinToString(" ") }
    }

    fun at(x: Int, y: Int): Cell<T>? {
        val value = cells.getOrNull(y - 1)?.getOrNull(x - 1)
        return if (value == null) null else Cell(value, x, y)
    }

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


data class Cell<out T>(val value: T, val x: Int, val y: Int) {
    fun toPoint(): Point = Point(x, y)
}

data class Point(val x: Int, val y: Int)
