package ioan

import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val dayNum = args.firstOrNull()?.toIntOrNull()
    val part = args.getOrNull(1)?.toIntOrNull()
    if (dayNum !in 1..25 || part !in 1..2) {
        System.err.println("Usage: ./gradlew run <1..25> <1|2>")
        exitProcess(1)
    }

    val day = getDay(dayNum!!)

    val result = if (part == 1) day.part1() else day.part2()

    println(result)
}

fun getDay(day: Int): Day =
    when (day) {
        1 -> Day1
        2 -> Day2
        3 -> Day3
        4 -> Day4
        else -> throw NotImplementedError("Haven't implemented that day yet")
    }
