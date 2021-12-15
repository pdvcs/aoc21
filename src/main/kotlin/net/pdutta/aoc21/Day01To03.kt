package net.pdutta.aoc21

class Day01To03(val debug: Boolean = false) {

    // Problem 6
    fun lifeSupportDiagnostic(diagnosticReportLines: List<String>): Int {
        return metricRating(diagnosticReportLines, metric = "oxygen") *
                metricRating(diagnosticReportLines, metric = "co2")
    }

    private fun metricRating(binaryLines: List<String>, metric: String): Int {
        debug("entering metricRating() with metric = $metric, binaryLines has ${binaryLines.size} items")

        // begin by looking at *all* indices in the input
        var indicesInScope = binaryLines.indices.toList()

        var indicesWithZeroes: MutableList<Int>
        var indicesWithOnes: MutableList<Int>
        var charPosition = -1

        // all lines have equal length
        val lineLength = binaryLines.first().length

        while (charPosition < lineLength) {
            charPosition++
            debug("charPos = $charPosition")

            var zeroesCount = 0
            var onesCount = 0
            indicesWithZeroes = mutableListOf()
            indicesWithOnes = mutableListOf()

            for (i in indicesInScope) {
                val line = binaryLines[i]
                if (line[charPosition] == '0') {
                    zeroesCount++
                    indicesWithZeroes.add(i)
                } else { // '1'
                    onesCount++
                    indicesWithOnes.add(i)
                }
            }

            // see https://adventofcode.com/2021/day/3 for what 'bit criteria' means
            // function returns '=' if zeroesCount == onesCount
            val bitCriteria = selectBitCriteria(metric, zeroesCount, onesCount)

            debug("indices of input lines with zeroes = $indicesWithZeroes")
            debug("indices of input lines with ones = $indicesWithOnes")
            val adjective = if (metric == "oxygen") "most" else "least"
            debug("$adjective-common char: $bitCriteria")

            val li = if (bitCriteria == '=') {
                if (metric == "co2") indicesWithZeroes else indicesWithOnes
            } else {
                if (bitCriteria == '0') indicesWithZeroes else indicesWithOnes
            }
            debug("setting indices to $li")

            if (li.size == 1) {
                val n = binaryLines[li.first()].toInt(radix = 2)
                println(">> $metric rating: ${binaryLines[li.first()]} ($n)")
                return n
            } else {
                indicesInScope = li
            }

        }
        return -200
    }

    private fun selectBitCriteria(metric: String, zeroesCount: Int, onesCount: Int): Char {
        if (zeroesCount == onesCount) return '='
        return when (metric) {
            "oxygen" -> // pick the most-common value
                if (zeroesCount > onesCount) '0' else '1'
            "co2" -> // pick the least-common value
                if (onesCount > zeroesCount) '0' else '1'
            else ->
                ' '
        }
    }

    // Problem 5
    fun binaryDiagnostic(binaryLines: List<String>): Int {
        val lineLength = binaryLines[0].length
        var sGamma = ""
        var sEpsilon = ""
        for (i in 0 until lineLength) {
            var count0 = 0
            var count1 = 0
            binaryLines.forEach { s ->
                if (s[i] == '0') count0++ else count1++
            }
            if (count0 == count1) {
                println(">> warning: count0 == count1 (= $count0) for i = $i")
            }
            if (count0 > count1) {
                sGamma += "0"
                sEpsilon += "1"
            } else {
                sGamma += "1"
                sEpsilon += "0"
            }
        }
        val gamma = sGamma.toInt(radix = 2)
        val epsilon = sEpsilon.toInt(radix = 2)
        println(">> gamma = $sGamma ($gamma), epsilon = $sEpsilon ($epsilon)")
        return gamma * epsilon
    }

    // Problem 4
    @Suppress("KotlinConstantConditions")
    fun diveCalcWithAim(commands: List<String>): Int {
        var hpos = 0
        var depth = 0
        var aim = 0
        for (c in commands) {
            val (cmd, arg) = c.split(" ")
            val iArg = arg.toInt()
            when (cmd) {
                "forward" -> {
                    hpos += iArg
                    depth += aim * iArg
                }
                "up" -> aim -= iArg
                "down" -> aim += iArg
            }
        }
        println(">> hpos = $hpos, depth = $depth")
        return hpos * depth
    }

    // Problem 3
    @Suppress("KotlinConstantConditions")
    fun diveCalc(commands: List<String>): Int {
        var hpos = 0
        var depth = 0
        for (c in commands) {
            val (cmd, arg) = c.split(" ")
            val iArg = arg.toInt()
            when (cmd) {
                "forward" -> hpos += iArg
                "up" -> depth -= iArg
                "down" -> depth += iArg
            }
        }
        println(">> hpos = $hpos, depth = $depth")
        return hpos * depth
    }

    // Problem 2
    fun measureSweepWithSlidingWindow(ms: List<Int>): Int {
        val w = mutableListOf<Int>()
        for (i in 0 until ms.size - 2) {
            w.add(ms[i] + ms[i + 1] + ms[i + 2])
        }
        return measureSweep(w)
    }

    // Problem 1
    fun measureSweep(ms: List<Int>): Int {
        var inc = 0
        for (i in ms.indices) {
            val m = ms[i]
            val mprev = if (i == 0) ms[0] else ms[i - 1]
            if (m > mprev) {
                inc++
            }
        }
        return inc
    }

    private fun debug(s: String) {
        if (this.debug) {
            Utils.logDebug(s)
        }
    }
}