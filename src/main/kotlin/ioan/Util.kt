package ioan

import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.readLines
import kotlin.io.path.readText
import kotlin.math.pow

val resourcesDir: Path = Paths.get("").resolve("src/main/resources")

fun readText(day: Int, test: Boolean = false): String {
    return resourcesDir.resolve(getFileName(day, test)).readText()
}

fun readLines(day: Int, test: Boolean = false): List<String> {
    return resourcesDir.resolve(getFileName(day, test)).readLines()
}

fun <T> List<T>.withoutIdx(idx: Int): List<T> =
    this.filterIndexed { index, _ -> index != idx }

private fun getFileName(day: Int, test: Boolean) =
    if (test) "day$day-test.txt" else "day$day.txt"


fun Int.pow(exponent: Int): Int = this.toDouble().pow(exponent.toDouble()).toInt()

fun <T> generatePairs(source: List<T>): List<Pair<T, T>> {
    val pairs = mutableListOf<Pair<T, T>>()
    source.forEachIndexed { idx, e ->
        source.subList(idx + 1, source.size).forEach { pairs.add(e to it) }
    }
    return pairs
}
