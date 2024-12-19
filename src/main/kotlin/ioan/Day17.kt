package ioan

object Day17 {

    fun part1(): String {
        val inp = readLines(17, false)

        var a = parseRegisterValue(inp[0])
        var aNonTrunc = a
        var b = parseRegisterValue(inp[1])
        var c = parseRegisterValue(inp[2])

        val program = parseProgram(inp[4])

        val out = mutableListOf<Int>()

        println("A = $a")
        println("B = $b")
        println("C = $c")
        println(program)
        println()

        var pointer = 0

        fun comboOperand(): Long {
            return when (val operand = program[pointer + 1]) {
                0, 1, 2, 3 -> operand.toLong()
                4 -> a
                5 -> b
                6 -> c
                else -> throw IllegalStateException("cannot process combo operand: $operand")
            }
        }

        fun literalOperand(): Int {
            return program[pointer + 1]
        }

        while (pointer < program.size) {
            require(pointer % 2 == 0)
            val opcode = program[pointer]
            println("pointer = $pointer")
//            println("opcode = $opcode")
//            println("operand = ${program[pointer + 1]}")
            when (opcode) {
                0 -> {
                    a /= 2.powLong(comboOperand())
                    pointer += 2
                }

                1 -> {
                    b = b xor literalOperand().toLong()
                    pointer += 2
                }

                2 -> {
                    b = comboOperand() % 8
                    pointer += 2
                }
                3 -> {
                    if (a == 0L)
                        pointer += 2
                    else
                        pointer = literalOperand()
                }
                4 -> {
                    b =  b xor c
                    pointer += 2
                }
                5 -> {
                    out.add((comboOperand() % 8).toInt())
                    pointer += 2
                }
                6 -> {
                    b = a / 2.powLong(comboOperand())
                    pointer += 2
                }
                7 -> {
                    c = a / 2.powLong(comboOperand())
                    pointer += 2
                }
            }
            println("A = $a")
//            println("B = $b")
//            println("C = $c\n")
        }

        return out.joinToString(",")
    }

    private fun parseRegisterValue(line: String): Long {
        val (_, value) = line.split(": ")
        return value.toLong()
    }

    private fun parseProgram(line: String): List<Int> {
        val (_, raw) = line.split(": ")
        return raw.split(",").map { it.toInt() }
    }

    fun part2(): Long {
        val inp = readLines(17, false)

        var b = parseRegisterValue(inp[1])
        var c = parseRegisterValue(inp[2])

        val program = parseProgram(inp[4])

        outer@ for (initA in 1..10_000_000_000L) {
            var a = initA

            val out = mutableListOf<Int>()

//            println("A = $a")
//            println("B = $b")
//            println("C = $c")
//            println(program)
//            println()

            var pointer = 0

            fun comboOperand(): Long {
                return when (val operand = program[pointer + 1]) {
                    0, 1, 2, 3 -> operand.toLong()
                    4 -> a
                    5 -> b
                    6 -> c
                    else -> throw IllegalStateException("cannot process combo operand: $operand")
                }
            }

            fun literalOperand(): Int {
                return program[pointer + 1]
            }

            while (pointer < program.size) {
//                require(pointer % 2 == 0)
                val opcode = program[pointer]
//                println("pointer = $pointer")
//                println("opcode = $opcode")
//                println("operand = ${program[pointer + 1]}")
                when (opcode) {
                    0 -> {
                        a /= 2.powLong(comboOperand())
                        pointer += 2
                    }

                    1 -> {
                        b = b xor literalOperand().toLong()
                        pointer += 2
                    }

                    2 -> {
                        b = comboOperand() % 8
                        pointer += 2
                    }

                    3 -> {
                        if (a == 0L)
                            pointer += 2
                        else
                            pointer = literalOperand()
                    }

                    4 -> {
                        b = b xor c
                        pointer += 2
                    }

                    5 -> {
                        out.add((comboOperand() % 8).toInt())
                        if (program.subList(0, out.size) != out) {
                            continue@outer
                        } else if (program == out) {
                            return initA
                        }

                        pointer += 2
                    }

                    6 -> {
                        b = a / 2.powLong(comboOperand())
                        pointer += 2
                    }

                    7 -> {
                        c = a / 2.powLong(comboOperand())
                        pointer += 2
                    }
                }
//                println("A = $a")
//                println("B = $b")
//                println("C = $c\n")
            }
        }

        return -1
    }

    fun part3(): Long {

        val inp = readLines(17, false)

        var a = 0L
        var b = parseRegisterValue(inp[1])
        var c = parseRegisterValue(inp[2])

        val program = parseProgram(inp[4])


        val out = mutableListOf(program)

//        println("A = $a")
//        println("B = $b")
//        println("C = $c")
//        println(program)
//        println()

        var pointer = program.size - 2

        fun comboOperand(): Long {
            return when (val operand = program[pointer + 1]) {
                0, 1, 2, 3 -> operand.toLong()
                4 -> a
                5 -> b
                6 -> c
                else -> throw IllegalStateException("cannot process combo operand: $operand")
            }
        }

        fun literalOperand(): Int {
            return program[pointer + 1]
        }

        while (pointer >= 0) {
//                require(pointer % 2 == 0)
            val opcode = program[pointer]
//                println("pointer = $pointer")
//                println("opcode = $opcode")
//                println("operand = ${program[pointer + 1]}")
            when (opcode) {
                0 -> {
                    a /= 2.powLong(comboOperand())
                    pointer -= 2
                }

                1 -> {
                    b = b xor literalOperand().toLong()
                    pointer -= 2
                }

                2 -> {
                    b = comboOperand() % 8
                    pointer -= 2
                }

                3 -> {
                    if (a == 0L)
                        pointer += 2
                    else
                        pointer = literalOperand()
                }

                4 -> {
                    b = b xor c
                    pointer += 2
                }

                5 -> {
                    out.removeLast()
                    if (out.isEmpty())
                        return a

                    pointer += 2
                }

                6 -> {
                    b = a / 2.powLong(comboOperand())
                    pointer += 2
                }

                7 -> {
                    c = a / 2.powLong(comboOperand())
                    pointer += 2
                }
            }
//                println("A = $a")
//                println("B = $b")
//                println("C = $c\n")
        }
        return -1
    }

}

