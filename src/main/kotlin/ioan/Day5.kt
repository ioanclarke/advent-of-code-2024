package ioan

typealias PageNum = Int
typealias Hierarchy = Map<PageNum, List<PageNum>> // values in the map are a list of numbers than the key must come before

object Day5 : Day {

    data class Update(val pageNums: List<PageNum>) {
        fun isValid(hierarchy: Hierarchy): Boolean = sortedByRules(hierarchy) == this

        fun sortedByRules(rules: Hierarchy): Update {
            val rulesThatApplyToUpdate = rules
                .filterKeys { it in pageNums }
                .mapValues { entry -> entry.value.filter { it in pageNums } }

            val hierarchy = rulesThatApplyToUpdate.mapValues { it.value.size }
            return Update(pageNums.sortedByDescending { hierarchy.getOrDefault(it, 0) })
        }

        val middleNumber get(): Int = pageNums[(pageNums.size - 1) / 2]
    }


    override fun part1(): Int {
        val (rulesRaw, updatesRaw) = readText(5).split("\n\n")
        val rules = buildHierarchy(rulesRaw)
        val updates = parseUpdates(updatesRaw)
        return updates
            .filter { it.isValid(rules) }
            .sumOf { it.middleNumber }
    }

    override fun part2(): Int {
        val (rulesRaw, updatesRaw) = readText(5).split("\n\n")
        val rules = buildHierarchy(rulesRaw)
        val updates = parseUpdates(updatesRaw)
        return updates
            .filterNot { it.isValid(rules) }
            .map { it.sortedByRules(rules) }
            .sumOf { it.middleNumber }
    }

    private fun buildHierarchy(rulesText: String): Hierarchy =
        rulesText
            .split("\n")
            .map { line ->
                val (fst, snd) = line.split("|").map { it.toInt() }
                fst to snd
            }
            .groupBy { it.first }
            .mapValues { entry -> entry.value.map { it.second } }

    private fun parseUpdates(updateText: String): List<Update> =
        updateText
            .trim()
            .split("\n")
            .map { line -> line.split(",").map { it.toInt() } }
            .map { Update(it) }
}
