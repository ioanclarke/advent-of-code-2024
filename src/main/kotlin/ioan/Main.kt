package ioan

import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val dayNum = args.firstOrNull()?.toIntOrNull()
    val part = args.getOrNull(1)
    if (dayNum !in 1..25 || part !in setOf("a", "b")) {
        System.err.println("Usage: ./gradlew run <1..25> <a|b>")
        exitProcess(1)
    }

    val day = getDay(dayNum!!)
    val result = if (part == "a") day.part1() else day.part2()

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

