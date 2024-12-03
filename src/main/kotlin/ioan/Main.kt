package ioan

import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val dayNum = args.firstOrNull()?.toIntOrNull()
    val part = args.getOrNull(1)
    if (dayNum !in 1..25 || part !in setOf("a", "b")) {
        System.err.println("Usage: ./gradlew run <1..25> <a|b>")
        exitProcess(1)
    }

    println(
        when (dayNum to part) {
            Pair(1, "a") -> Day1.a()
            Pair(1, "b") -> Day1.b()
            Pair(2, "a") -> Day2.a()
            Pair(2, "b") -> Day2.b()
            Pair(3, "a") -> Day3.a()
            Pair(3, "b") -> Day3.b()
            else -> "Not implemented yet"
        }
    )
}

