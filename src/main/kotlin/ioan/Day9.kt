package ioan

object Day9 : Day {

    private const val FREE_SPACE = -1L

    override fun part1(): Long {
        val (diskMap, _) = buildDiskMap()


        while (true) {
            val firstFreeSpace = diskMap.indexOf(-1)
            if (firstFreeSpace < 0) break

            val fileBlock = diskMap.removeLast()
            if (fileBlock == FREE_SPACE) continue

            diskMap[firstFreeSpace] = fileBlock
        }

        return getChecksum(diskMap)
    }

    override fun part2(): Long {
        val (diskMap, fileSizes) = buildDiskMap()

        val fileIds = diskMap.distinct().filter { it != FREE_SPACE }

        for (fileId in fileIds.reversed()) {
            var idx = 0
            val fileIdCurrentPos = diskMap.indexOf(fileId)
            while (idx < fileIdCurrentPos) {
                val el = diskMap[idx]
                if (el != FREE_SPACE) {
                    idx++
                    continue
                }

                val fileSize = fileSizes[fileId]!!

                if ((idx..<idx + fileSize).all { diskMap.getOrNull(it) == FREE_SPACE }) {
                    for (i in fileIdCurrentPos..<fileIdCurrentPos + fileSize) {
                        diskMap[i] = FREE_SPACE
                    }

                    for (i in (idx..<idx + fileSize)) {
                        diskMap[i] = fileId
                    }
                    break
                }

                idx += 2
            }
        }

        return getChecksum(diskMap)
    }

    private fun buildDiskMap(): Pair<MutableList<Long>, Map<Long, Int>> {
        val diskMap = readText(9).toDigits()
        val expandedDiskMap = mutableListOf<Long>()
        val fileSizes = mutableMapOf<Long, Int>()
        diskMap.forEachIndexed { idx, length ->
            val toPrint = if (idx.isEven) {
                val fileId = idx / 2L
                fileSizes[fileId] = length
                fileId
            } else {
                FREE_SPACE
            }
            repeat(length) { expandedDiskMap.add(toPrint) }
        }
        return expandedDiskMap to fileSizes

    }

    private fun getChecksum(diskMap: List<Long>): Long =
        diskMap.mapIndexed { idx, fileId -> if (fileId == FREE_SPACE) 0 else idx * fileId }.sum()
}
