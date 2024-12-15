package ioan

object Day13 : Day {

    data class Equation(val xCoefficient: Long, val yCoefficient: Long, val result: Long) {
        operator fun times(n: Long): Equation = Equation(xCoefficient * n, yCoefficient * n, result * n)
    }

    override fun part1(): Long = solve(isPartTwo = false)

    override fun part2(): Long = solve(isPartTwo = true)

    private fun solve(isPartTwo: Boolean): Long =
        readText(13)
            .split("\n\n")
            .map { parseEquationPair(it, isPartTwo) }
            .sumOf {
                val (aPresses, bPresses) = solveSimultaneousEquations(it) ?: return@sumOf 0
                getNumberOfTokens(aPresses, bPresses)
            }

    private val buttonRegex = "^Button [A-B]: X\\+(\\d+), Y\\+(\\d+)$".toRegex()
    private val prizeRegex = "^Prize: X=(\\d+), Y=(\\d+)$".toRegex()

    private fun parseEquationPair(raw: String, isPartTwo: Boolean): Pair<Equation, Equation> {
        val parseCoefficients: (String) -> Pair<Long, Long> = {
            val match = buttonRegex.find(it.trim())!!
            match.groupValues[1].toLong() to match.groupValues[2].toLong()
        }

        val parsePrize: (String) -> Pair<Long, Long> = {
            val match = prizeRegex.find(it.trim())!!
            val valueToAdd = if (isPartTwo) 10000000000000 else 0L

            match.groupValues[1].toLong() + valueToAdd to match.groupValues[2].toLong() + valueToAdd
        }

        val (buttonA, buttonB, prize) = raw.split("\n")

        val buttonAValues = parseCoefficients(buttonA)
        val buttonBValues = parseCoefficients(buttonB)
        val prizeValues = parsePrize(prize)

        return Equation(buttonAValues.first, buttonBValues.first, prizeValues.first) to
                Equation(buttonAValues.second, buttonBValues.second, prizeValues.second)
    }

    private fun solveSimultaneousEquations(equations: Pair<Equation, Equation>): Pair<Long, Long>? {
        val firstMultipliedEquation = equations.first * equations.second.xCoefficient
        val secondMultipliedEquation = equations.second * equations.first.xCoefficient

        val yCoefficientDiff = firstMultipliedEquation.yCoefficient - secondMultipliedEquation.yCoefficient
        val resultDiff = firstMultipliedEquation.result - secondMultipliedEquation.result

        val y = resultDiff / yCoefficientDiff.toDouble()
        if (y % 1.0 != 0.0) return null

        val x = (firstMultipliedEquation.result - y * firstMultipliedEquation.yCoefficient) / firstMultipliedEquation.xCoefficient
        if (x % 1.0 != 0.0) return null

        return x.toLong() to y.toLong()
    }

    private fun getNumberOfTokens(aPresses: Long, bPresses: Long): Long = aPresses * 3L + bPresses
}
