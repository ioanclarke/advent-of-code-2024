package ioan

import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.readLines
import kotlin.io.path.readText
import kotlin.math.pow

private val resourcesDir: Path = Paths.get("").resolve("src/main/resources")

fun readText(day: Int, test: Boolean = false): String = getFile(day, test).readText()

fun readLines(day: Int, test: Boolean = false): List<String> = getFile(day, test).readLines()

private fun getFile(day: Int, test: Boolean): Path =
    resourcesDir.resolve(if (test) "day$day-test.txt" else "day$day.txt")

fun <T> List<T>.withoutIdx(idx: Int): List<T> =
    this.filterIndexed { index, _ -> index != idx }

fun Int.pow(exponent: Int): Int = this.toDouble().pow(exponent.toDouble()).toInt()

val Int.isEven: Boolean get() = this % 2 == 0

val Long.isEven: Boolean get() = this % 2 == 0L

val Int.digitCount: Int get() = this.toString().length

val Long.digitCount: Int get() = this.toString().length

fun <T> generatePairs(source: List<T>): List<Pair<T, T>> {
    val pairs = mutableListOf<Pair<T, T>>()
    source.forEachIndexed { idx, e ->
        source.subList(idx + 1, source.size).forEach { pairs.add(e to it) }
    }
    return pairs
}

fun String.toDigits(): List<Int> = this.trim().chunked(1).map { it.toInt() }
