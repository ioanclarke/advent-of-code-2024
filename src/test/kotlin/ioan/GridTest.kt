package ioan

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class GridTest {


    @Test
    fun `at - valid coords`() {
        val grid = Grid(listOf(listOf("a", "b", "c")))

        assertEquals(Cell("a", 1, 1), grid.at(1, 1))
    }

    @Test
    fun `at - invalid coords`() {
        val grid = Grid(listOf(listOf("a", "b", "c")))

        assertEquals(null, grid.at(4, 1))
    }

    @Test
    fun iterator() {
        val grid = Grid(
            listOf(
                listOf("a", "b", "c"),
                listOf("d", "e", "f")
            )
        )

        val cells = grid.iterator().asSequence().toList()

        assertEquals(
            listOf(
                Cell("a", 1, 1),
                Cell("b", 2, 1),
                Cell("c", 3, 1),
                Cell("d", 1, 2),
                Cell("e", 2, 2),
                Cell("f", 3, 2),
            ),
            cells
        )
    }
}
