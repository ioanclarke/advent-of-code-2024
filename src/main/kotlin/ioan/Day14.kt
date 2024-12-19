package ioan

import kotlin.math.abs

typealias Velocity = Point

object Day14 : Day {

    data class Robot(val position: Point, val velocity: Velocity) {
        override fun toString(): String {
            return "${position.x},${position.y}"
        }
    }

    private const val SPACE_HEIGHT = 103
    private const val SPACE_WIDTH = 101

    // 54916290 too low
    override fun part1(): Int {
        var robots = readLines(14, false)
            .map(::parseRobot)

        repeat(100) {
            robots = robots.map {

                var newX = it.position.x + it.velocity.x
                if (newX < 0) {
                    newX = SPACE_WIDTH + newX
                }

                var newY = it.position.y + it.velocity.y
                if (newY < 0) {
                    newY = SPACE_HEIGHT + newY

                }

                Robot(
                    Point(
                        newX % SPACE_WIDTH,
                        newY % SPACE_HEIGHT
                    ),
                    it.velocity
                )
            }

            val grid: MutableList<MutableList<Char>> =
                MutableList(SPACE_HEIGHT, { _ -> MutableList(SPACE_WIDTH, { _ -> '.' }) })
            robots.forEach {
                val (x, y) = it.position
                grid[y][x] = 'X'
            }
            println(grid.joinToString(separator = "\n", postfix = "\n\n", transform = { it.joinToString(" ") }))


//            val row = List<Char>()
//            robots.forEach { println(it) }
        }

        val northEastQuadrantCount = robots
            .filter { it.position.x > SPACE_WIDTH / 2 && it.position.y < SPACE_HEIGHT / 2 }
            .count()

        println(northEastQuadrantCount)

        val southEastQuadrantCount = robots
            .filter { it.position.x > SPACE_WIDTH / 2 && it.position.y > SPACE_HEIGHT / 2 }
            .count()

        println(southEastQuadrantCount)

        val southWestQuadrantCount = robots
            .filter { it.position.x < SPACE_WIDTH / 2 && it.position.y > SPACE_HEIGHT / 2 }
            .count()

        println(southWestQuadrantCount)

        val northWestQuadrantCount = robots
            .filter { it.position.x < SPACE_WIDTH / 2 && it.position.y < SPACE_HEIGHT / 2 }
            .count()

        println(northWestQuadrantCount)

        return northEastQuadrantCount * southEastQuadrantCount * southWestQuadrantCount * northWestQuadrantCount
    }

    private val robotRegex = "p=(\\d+),(\\d+) v=(-?\\d+),(-?\\d+)".toRegex()

    fun parseRobot(line: String): Robot {
        val match = robotRegex.find(line)!!
        val startingX = match.groupValues[1].toInt()
        val startingY = match.groupValues[2].toInt()
        val velX = match.groupValues[3].toInt()
        val velY = match.groupValues[4].toInt()

        return Robot(Point(startingX, startingY), Velocity(velX, velY))
    }

    // 1,000 too low
    // 50,000 too high
    override fun part2(): Int {
        var robots = readLines(14, false)
            .map(::parseRobot)

        for (i in 1..50_000) {
            robots = robots.map {

                var newX = it.position.x + it.velocity.x
                if (newX < 0) {
                    newX = SPACE_WIDTH + newX
                }

                var newY = it.position.y + it.velocity.y
                if (newY < 0) {
                    newY = SPACE_HEIGHT + newY

                }

                Robot(
                    Point(
                        newX % SPACE_WIDTH,
                        newY % SPACE_HEIGHT
                    ),
                    it.velocity
                )
            }

            val grid: MutableList<MutableList<Char>> =
                MutableList(SPACE_HEIGHT, { _ -> MutableList(SPACE_WIDTH, { _ -> '.' }) })
            robots.forEach {
                val (x, y) = it.position
                grid[y][x] = 'X'
            }
//            println("$i\n${grid.joinToString(separator = "\n", postfix = "\n\n", transform = {it.joinToString(" ")})}")
//            val eastQuadrantCount = robots
//                .filter { it.position.x > SPACE_WIDTH / 2 }
//                .count()
//
//            val westQuadrantCount = robots
//                .filter { it.position.x < SPACE_WIDTH / 2 }
//                .count()
//
//            val diff = abs(eastQuadrantCount - westQuadrantCount)
//            if (diff == 0) {
//                return i
//            }

            val northEastQuadrantCount = robots
                .filter { it.position.x > SPACE_WIDTH / 2 && it.position.y < SPACE_HEIGHT / 2 }
                .count()

//            println(northEastQuadrantCount)

            val southEastQuadrantCount = robots
                .filter { it.position.x > SPACE_WIDTH / 2 && it.position.y > SPACE_HEIGHT / 2 }
                .count()

//            println(southEastQuadrantCount)

            val southWestQuadrantCount = robots
                .filter { it.position.x < SPACE_WIDTH / 2 && it.position.y > SPACE_HEIGHT / 2 }
                .count()

//            println(southWestQuadrantCount)

            val northWestQuadrantCount = robots
                .filter { it.position.x < SPACE_WIDTH / 2 && it.position.y < SPACE_HEIGHT / 2 }
                .count()
//            Thread.sleep(50)

            val positions = robots.map { it.position }
            val distinctPositions = positions.distinct()

            if (
//                northEastQuadrantCount == northWestQuadrantCount &&
//                southEastQuadrantCount == southWestQuadrantCount &&
//                southEastQuadrantCount > northEastQuadrantCount &&
                distinctPositions.size == 500
                ) {
                return i
            }

        }

        return -1
    }
}
