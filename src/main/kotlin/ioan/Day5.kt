package ioan


typealias Update = List<Int>

typealias Rules = MutableMap<Int, MutableList<Int>> // values are a list of numbers than the key must come before

object Day5 : Day {

    data class Rule(val number: Int, val comesBefore: List<Int>)

    override fun part1(): Int {
        val text  = readText(5, test = false)
        val (rulesRaw, updatesRaw) = text.split("\n\n")
//        println(rulesRaw)
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


//        println(rulesBuilder)



        val validUpdates = updates.filter { isValid(it, rules) }
        val middleNumbers = validUpdates.map(::getMiddleNumber)
        return middleNumbers.sum()
    }

    private fun isValid(update: Update, rules: Map<Int, List<Int>>): Boolean {
        for (i in update.size - 1 downTo 1 step 1) {
            val num = update[i]
            val numsBeforeNum = update.subList(0, i)
//            println(num)
//            println(numsBeforeNum)
            val numMustComeBeforeThese = rules[num] ?: continue
            if (numsBeforeNum.any { it in numMustComeBeforeThese }) {
                return false
            }
        }

        return true
    }

    private fun getMiddleNumber(update: Update): Int {
        return update[(update.size - 1) / 2]
    }


    override fun part2(): Int {
        TODO()
    }
}
