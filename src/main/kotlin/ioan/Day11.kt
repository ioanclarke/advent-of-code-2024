package ioan

object Day11 : Day {

    override fun part1(): Int {
        for (i in 0..10000) {
            println("$i - ${(i * 2024).digitCount.isEven}")
        }
        val inp = readText(11, test = true).trim()
//        println(inp)
        val stones = inp.split(" ").map { it.toLong() }.toMutableList()
        println(stones.map { if (it.digitCount.isEven) "E" else "O"})

        repeat(10) {
            var idx = 0
            while (idx < stones.size) {
                val stone = stones[idx]
                if (stone == 0L) {
                    stones[idx] = 1
                    idx ++
                } else if (stone.digitCount.isEven) {
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
            println(stones.map { if (it.digitCount.isEven) "E" else "O"})
        }

        return stones.size
    }

    // 128805502478437 too low
    override fun part2(): Int {
        val inp = readText(11, test = false).trim()
//        println(inp)
        var stones = inp.split(" ").map { it.toLong() }.toLongArray()

//        var loop = 0
//        println("$loop - ${stones.size}")
//        var prevSize = stones.size

        var newStones = LongArray(stones.size * 2)


        repeat(25) {
//            loop++
            var idx = 0
            while (idx < stones.size) {
                val stone = stones[idx]
                if (stone == 0L) {
                    newStones[idx] = 1
                    idx ++
                } else if (stone.toString().length.isEven) {
                    val digits = stone.toString()
                    val leftHalf = digits.substring(0, digits.length / 2)
                    val rightHalf = digits.substring(digits.length / 2)
                    newStones[idx] = leftHalf.toLong()
                    newStones[idx + 1] = rightHalf.toLong()
                    idx += 2
                } else {
                    newStones[idx] = stone * 2024
                    idx ++
                }
            }
            stones = newStones
            newStones = LongArray(stones.size * 2)


//            println("$loop - ${stones.size}")
//            println("${stones.size / prevSize.toDouble()}")
//            prevSize = stones.size
//            println(stones)
        }

        return stones.size
    }
}
