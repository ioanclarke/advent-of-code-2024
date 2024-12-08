package ioan

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    println(Day8.part2())
    return

    val dayNum = args.firstOrNull()?.toIntOrNull()
    val part = args.getOrNull(1)?.toIntOrNull()
    validateArgs(dayNum, part)

    val day = getDay(dayNum)

    val result = if (part == 1) day.part1() else day.part2()

    println(result)
}

fun <T> generatePermutations(elements: List<T>, length: Int): List<List<T>> {
    if (length == 0) return listOf(emptyList())
    val smallerPermutations = generatePermutations(elements, length - 1)
    return smallerPermutations.flatMap { perm ->
        elements.map { element -> perm + element }
    }
}

fun main() {
    val elements = listOf(0, 1) // The possible values for each position
    val x = 2 // Length of the list
    val result = generatePermutations(elements, x)
    println(result)
}

fun getDay(day: Int): Day =
    when (day) {
        1 -> Day1
        2 -> Day2
        3 -> Day3
        4 -> Day4
        5 -> Day5
        6 -> Day6
        7 -> Day7
        else -> throw NotImplementedError("Haven't implemented that day yet")
    }

@OptIn(ExperimentalContracts::class)
fun validateArgs(dayNum: Int?, part: Int?) {
    contract {
        returns() implies (dayNum != null && part != null)
    }

    if (dayNum !in 1..25 || part !in 1..2) {
        System.err.println("Usage: ./gradlew run <1..25> <1|2>")
        exitProcess(1)
    }
}
