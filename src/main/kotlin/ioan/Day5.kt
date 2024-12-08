package ioan

typealias PageNum = Int
typealias Rules = Map<PageNum, List<PageNum>> // values in the map are a list of numbers than the key must come before
typealias Update = List<Int>

object Day5 : Day {

    override fun part1(): Int {
        val (rulesRaw, updatesRaw) = readText(5).split("\n\n")
        val rules = buildRules(rulesRaw)
        val updates = parseUpdates(updatesRaw)
        return updates
            .filter { isValid(it, rules) }
            .map(::getMiddleNumber)
            .sum()
    }

    override fun part2(): Int {
        val (rulesRaw, updatesRaw) = readText(5).split("\n\n")
        val rules = buildRules(rulesRaw)
        val updates = parseUpdates(updatesRaw)
        return updates
            .filterNot { isValid(it, rules) }
            .map { sortByHierarchy(it, rules) }
            .map(::getMiddleNumber)
            .sum()
    }

    // values in the map are a list of numbers than the key must come before
    private fun buildRules(rulesText: String): Rules =
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

    private fun isValid(update: Update, rules: Rules): Boolean =
        sortByHierarchy(update, rules) == update

    private fun sortByHierarchy(update: Update, rules: Rules): Update {
        val rulesThatApplyToUpdate = rules
            .filterKeys { it in update }
            .mapValues { entry -> entry.value.filter { it in update } }

        val hierarchy = rulesThatApplyToUpdate.mapValues { it.value.size }
        return update.sortedByDescending { hierarchy.getOrDefault(it, 0) }
    }

    private fun getMiddleNumber(update: Update): Int = update[(update.size - 1) / 2]
}
