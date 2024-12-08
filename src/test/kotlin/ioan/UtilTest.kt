package ioan

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class UtilTest {

    @Test
    fun `generatePairs - non empty`() {
        val pairs = generatePairs(listOf(1, 2, 3, 4))

        assertEquals(listOf(
            1 to 2,
            1 to 3,
            1 to 4,
            2 to 3,
            2 to 4,
            3 to 4
        ), pairs)
    }

    @Test
    fun `generatePairs - empty`() {
        val pairs = generatePairs(emptyList<Any>())

        assertEquals(emptyList<Any>(), pairs)
    }
}
