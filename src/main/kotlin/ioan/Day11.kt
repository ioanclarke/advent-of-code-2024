package ioan

object Day11 : Day {

    override fun part1(): Int {
        val inp = readText(11, test = false).trim()
//        println(inp)
        val stones = inp.split(" ").map { it.toLong() }.toMutableList()

        repeat(25) {
            var idx = 0
            while (idx < stones.size) {
                val stone = stones[idx]
                if (stone == 0L) {
                    stones[idx] = 1
                    idx ++
                } else if (stone.toString().length.isEven) {
                    val digits = stone.toString()
                    val leftHalf = digits.substring(0, digits.length / 2)
                    val rightHalf = digits.substring(digits.length / 2)
                    stones[idx] = leftHalf.toLong()
                    stones.add(idx + 1, rightHalf.toLong())
                    idx += 2
                } else {
                    stones[idx] = stone * 2024
                    idx ++
                }
            }
//            println(stones)
        }

        return stones.size
    }

    override fun part2(): Int {
        TODO()
        val inp = readText(11, test = false).trim()
//        println(inp)
        val stones = inp.split(" ").map { it.toLong() }.toMutableList()

        var loop = 0
        println("$loop - ${stones.size}")
        var prevSize = stones.size
        repeat(30) {
            loop++
            var idx = 0
            while (idx < stones.size) {
                val stone = stones[idx]
                if (stone == 0L) {
                    stones[idx] = 1
                    idx ++
                } else if (stone.toString().length.isEven) {
                    val digits = stone.toString()
                    val leftHalf = digits.substring(0, digits.length / 2)
                    val rightHalf = digits.substring(digits.length / 2)
                    stones[idx] = leftHalf.toLong()
                    stones.add(idx + 1, rightHalf.toLong())
                    idx += 2
                } else {
                    stones[idx] = stone * 2024
                    idx ++
                }
            }
            println("$loop - ${stones.size}")
            println("${stones.size / prevSize.toDouble()}")
            prevSize = stones.size
//            println(stones)
        }

        return stones.size
    }
}
