package ioan


typealias Update = List<Int>

typealias Rules = MutableMap<Int, MutableList<Int>> // values are a list of numbers than the key must come before

typealias PageNum = String

object Day5 : Day {

    override fun part1(): Int {
        val text = readText(5)
        val (rulesRaw, updatesRaw) = text.split("\n\n")
        val lines = updatesRaw.trim().split("\n")
        val updates: List<Update> = lines.map { line -> line.split(",").map { it.toInt() } }

        val rulesBuilder: Rules = mutableMapOf()

        for (ruleRaw in rulesRaw.split("\n")) {
            val (fst, snd) = ruleRaw.split("|").map { it.toInt() }
            val currentNums = rulesBuilder.getOrDefault(fst, mutableListOf())
            currentNums.add(snd)
            rulesBuilder[fst] = currentNums
        }

        val rules: Map<Int, List<Int>> = rulesBuilder.toMap()

        val validUpdates = updates.filter { isValid(it, rules) }
        val middleNumbers = validUpdates.map(::getMiddleNumber)
        return middleNumbers.sum()
    }



    private fun getMiddleNumber(update: Update): Int {
        return update[(update.size - 1) / 2]
    }

    override fun part2(): Int {
        val text = readText(5, test = false)
        val (rulesRaw, updatesRaw) = text.split("\n\n")
        val lines = updatesRaw.trim().split("\n")
        val updates: List<Update> = lines.map { line -> line.split(",").map { it.toInt() } }

        val rulesBuilder: Rules = mutableMapOf()

        for (ruleRaw in rulesRaw.split("\n")) {
            val (fst, snd) = ruleRaw.split("|").map { it.toInt() }
            val currentNums = rulesBuilder.getOrDefault(fst, mutableListOf())
            currentNums.add(snd)
            rulesBuilder[fst] = currentNums
        }

        val rules: Map<Int, List<Int>> = rulesBuilder.toMap()

        val inValidUpdates = updates.filterNot { isValid(it, rules) }

        val middleNumbers = inValidUpdates.map{ sortByHierarchy(it, rules) }.map(::getMiddleNumber)
        return middleNumbers.sum()
    }

    private fun isValid(update: Update, rules: Map<Int, List<Int>>): Boolean =
        sortByHierarchy(update, rules) == update

    private fun sortByHierarchy(update: Update, rules:  Map<Int, List<Int>>): Update {
        val rulesThatApplyToUpdate = rules
            .filterKeys { it in update }
            .mapValues { entry -> entry.value.filter { it in update } }

        val hierarchy = rulesThatApplyToUpdate.mapValues { it.value.size }

        val sortedByHierarchy = update.sortedByDescending { hierarchy.getOrDefault(it, 0) }

        return sortedByHierarchy
    }
}
