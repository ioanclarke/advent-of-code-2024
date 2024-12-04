package ioan

typealias Report = List<Int>

object Day2 : Day{

    override fun part1(): Int {
        val lines = readLines(2)
        val reports = parseReports(lines)
        return reports.count(::isSafe)
    }

    override fun part2(): Int {
        val lines = readLines(2)
        val reports: List<Report> = parseReports(lines)
        val (safe, unsafe) = reports.partition(::isSafe)

        return safe.size + unsafe.filter(::isSafeWithDampener).size
    }

    private fun parseReports(lines: List<String>): List<Report> =
        lines.map { line -> line.split(" ").map { it.toInt() } }

    private fun isSafe(report: Report): Boolean {
        val first = report.first()
        val last = report.last()

        if (first == last) {
            return false
        }

        val windows: List<Pair<Int, Int>> = report.windowed(2).map { it[0] to it[1] }

        if (first < last) {
            return windows.all { it.second - it.first in 1..3 }
        }

        return windows.all { it.first - it.second in 1..3 }
    }

    private fun isSafeWithDampener(report: Report): Boolean =
        report.indices.any { isSafe(report.withoutIdx(it)) }
}
