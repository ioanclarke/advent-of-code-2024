package ioan

import kotlin.math.abs

typealias Frequency = Char

object Day8 : Day {

    override fun part1(): Int {
        val grid = Grid.from(readLines(8))
        val antennas: Map<Frequency, List<Point>> = buildAntennas(grid)
        return antennas.entries
            .flatMap { getAntinodeLocations1(it.value) }
            .distinct()
            .count { grid.at(it.x, it.y) != null }
    }

    private fun getAntinodeLocations1(locations: List<Point>): List<Point> {
        val pairwiseLocations: List<Pair<Point, Point>> = generatePairs(locations)
        // Within these pairs, fst will always be 'above' (have a lower y) than snd
        return pairwiseLocations.flatMap { (fst, snd) ->
            val xDiff = abs(fst.x - snd.x)
            val yDiff = abs(fst.y - snd.y)

            // If points have same x coord, then xDiff is 0 so it doesn't matter whether we
            //  add or substract
            if (fst.x > snd.x) {
                val p1 = Point(fst.x + xDiff, fst.y - yDiff)
                val p2 = Point(snd.x - xDiff, snd.y + yDiff)
                listOf(p1, p2)
            } else {
                val p1 = Point(fst.x - xDiff, fst.y - yDiff)
                val p2 = Point(snd.x + xDiff, snd.y + yDiff)
                listOf(p1, p2)
            }
        }
    }

    override fun part2(): Int {
        val grid = Grid.from(readLines(8))
        val antennas: Map<Frequency, List<Point>> = buildAntennas(grid)
        return antennas.entries
            .flatMap { getAntinodeLocations2(it.value, grid) }
            .distinct()
            .count()
    }

    private fun getAntinodeLocations2(locations: List<Point>, grid: Grid<Char>): List<Point> {
        val pairwiseLocations: List<Pair<Point, Point>> = generatePairs(locations)

        val nonAntennaAntinodes = pairwiseLocations.flatMap { (fst, snd) ->
            val xDiff = abs(fst.x - snd.x)
            val yDiff = abs(fst.y - snd.y)

            // If points have same x coord, then xDiff is 0 so it doesn't matter whether we
            //  add or substract
            if (fst.x > snd.x) {
                val northEastPoints = mutableListOf<Point>()

                var steps = 1

                while(true) {
                    val point = Point(fst.x + steps * xDiff, fst.y - steps * yDiff)
                    if (grid.at(point.x, point.y) != null) {
                        northEastPoints.add(point)
                        steps++
                    } else {
                        break
                    }
                }

                val southWestPoints = mutableListOf<Point>()

                steps = 1

                while(true) {
                    val point = Point(fst.x - steps * xDiff, fst.y + steps * yDiff)
                    if (grid.at(point.x, point.y) != null) {
                        southWestPoints.add(point)
                        steps++
                    } else {
                        break
                    }
                }

                northEastPoints + southWestPoints
            } else {
                val northWestPoints = mutableListOf<Point>()

                var steps = 1

                while(true) {
                    val point = Point(fst.x - steps * xDiff, fst.y - steps * yDiff)
                    if (grid.at(point.x, point.y) != null) {
                        northWestPoints.add(point)
                        steps++
                    } else {
                        break
                    }
                }

                val southEastPoints = mutableListOf<Point>()

                steps = 1

                while(true) {
                    val point = Point(fst.x + steps * xDiff, fst.y + steps * yDiff)
                    if (grid.at(point.x, point.y) != null) {
                        southEastPoints.add(point)
                        steps++
                    } else {
                        break
                    }
                }

                northWestPoints + southEastPoints
            }
        }

        return locations + nonAntennaAntinodes
    }

    private fun buildAntennas(grid: Grid<Char>): Map<Frequency, List<Point>> {
        return grid
            .filter { it.value != '.' }
            .groupBy { it.value }
            .mapValues { entry -> entry.value.map { it.toPoint() } }
    }

}
