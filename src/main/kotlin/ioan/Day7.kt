package ioan

typealias NumOfOps = Int
typealias Operation = (Long, Long) -> Long

object Day7 : Day {

    private val add: (Long, Long) -> Long = Long::plus
    private val multiply: (Long, Long) -> Long = Long::times
    private val concat: (Long, Long) -> Long = { x, y -> (x.toString() + y.toString()).toLong() }

    private data class Equation(val result: Long, val numbers: List<Long>) {
        fun isValid(opCombos: List<List<Operation>>): Boolean = result in getAllResults(opCombos)

        private fun getAllResults(opCombos: List<List<Operation>>): Sequence<Long> =
            opCombos
                .asSequence()
                .map {
                    numbers.drop(1)
                        .zip(it)
                        .fold(numbers.first()) { acc, (num, op) -> op(acc, num) }
                }
    }

    override fun part1(): Long {
        val lines = readLines(7)
        val equations = parseEquations(lines)
        val numOfOpsToOpCombos = buildMapOfNumberOfOpsToOpCombos(
            lines,
            2
        ) {
            when (it) {
                '0' -> add
                '1' -> multiply
                else -> throw IllegalStateException("never reached")
            }
        }

        return getSumOfValidEquations(equations, numOfOpsToOpCombos)
    }

    override fun part2(): Long {
        val lines = readLines(7)
        val equations = parseEquations(lines)
        val numOfOpsToOpCombos = buildMapOfNumberOfOpsToOpCombos(
            lines,
            3
        ) {
            when (it) {
                '0' -> add
                '1' -> multiply
                '2' -> concat
                else -> throw IllegalStateException("never reached")
            }
        }

        return getSumOfValidEquations(equations, numOfOpsToOpCombos)
    }

    private fun parseEquations(lines: List<String>): List<Equation> =
        lines
            .map { it.split(": ") }
            .map {
                Equation(
                    it.first().toLong(),
                    it[1].split(" ").map { num -> num.toLong() })
            }

    private fun buildMapOfNumberOfOpsToOpCombos(
        lines: List<String>,
        numberOfTypesOfOps: Int,
        charToOpMapper: (Char) -> Operation
    ): Map<NumOfOps, List<List<Operation>>> {
        val maxNumberOfOpsNeeded = lines.maxOf { line -> line.count { it == ' ' } } - 1
        return (1..maxNumberOfOpsNeeded).associateWith { numOfOps ->
            (0..<numberOfTypesOfOps.pow(numOfOps)).map { num ->
                val binaryRep = num.toString(numberOfTypesOfOps).padStart(numOfOps, '0').toList()
                binaryRep.map(charToOpMapper)
            }
        }
    }

    private fun getSumOfValidEquations(
        equations: List<Equation>,
        numOfOpsToOpCombos: Map<NumOfOps, List<List<Operation>>>
    ): Long {
        val validEquations = equations.filter { it.isValid(numOfOpsToOpCombos[it.numbers.size - 1]!!) }
        return validEquations.sumOf { it.result }
    }
}
