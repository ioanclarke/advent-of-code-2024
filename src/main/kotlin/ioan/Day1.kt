package ioan

import kotlin.math.abs

object Day1 {

    fun a(): Int {
        val (list1, list2) = getLists()
        return list1.zip(list2).sumOf { (a, b) -> abs(b - a) }
    }

    fun b(): Int {
        val (list1, list2) = getLists()
        val list2Counts: Map<Int, Int> = list2.groupingBy { it }.eachCount()
        return list1.sumOf { it * list2Counts.getOrDefault(it, 0) }
    }

    private fun getLists(): Pair<List<Int>, List<Int>> {
        val whitespace = "\\s+".toRegex()
        return readLines(1)
            .map { line -> line.split(whitespace).map { it.toInt() } }
            .map { it[0] to it[1] }
            .unzip()
            .let { (fst, snd) -> Pair(fst.sorted(), snd.sorted()) }
    }

}
