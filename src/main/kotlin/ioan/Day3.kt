package ioan

object Day3 {

    fun a(): Int {
        val text = readText(3)
        val validMul = "mul\\((\\d{1,3}),(\\d{1,3})\\)".toRegex()
        return validMul.findAll(text).sumOf {
            it.groupValues[1].toInt() * it.groupValues[2].toInt()
        }
    }

    fun b(): Int {
        val text = readText(3)
        val needle = "(do\\(\\)|don't\\(\\)|mul\\((\\d{1,3}),(\\d{1,3})\\))".toRegex()
        val results = needle.findAll(text)

        var count = 0
        var enabled = true
        for (result in results) {
            when (result.value) {
                "do()" -> enabled = true
                "don't()" -> enabled = false
                else -> {
                    if (enabled) {
                        count += result.groupValues[2].toInt() * result.groupValues[3].toInt()
                    }
                }
            }
        }

        return count
    }
}
