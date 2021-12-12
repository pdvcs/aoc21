package net.pdutta.aoc21

import java.io.File

// AoC 2021 - https://adventofcode.com/2021

var debug = false

fun main() {
    day01()
    day02()
    day03()
}

fun day03() {
    val commands = File("src/main/resources/input03.txt").readText(Charsets.UTF_8)
    val diagList = commands.split("\n")
    println("Problem 5: ${binaryDiagnostic(diagList)}")
    println("Problem 6: ${lifeSupportDiagnostic(diagList)}")
}

fun day02() {
    val commands = File("src/main/resources/input02.txt").readText(Charsets.UTF_8)
    val clist = commands.split("\n")
    println("Problem 3: ${diveCalc(clist)}")
    println("Problem 3: ${diveCalcWithAim(clist)}")
}

@Suppress("unused")
fun day01() {
    val report = File("src/main/resources/input01.txt").readText(Charsets.UTF_8)
    val ms = report.split("\n").map { it.toInt() }
    println("Problem 1: ${measureSweep(ms)}")
    println("Problem 2: ${measureSweepWithSlidingWindow(ms)}")
}

// Problem 6
fun lifeSupportDiagnostic(diagnosticReportLines: List<String>): Int {
    return lifeSupportMetricRating(diagnosticReportLines, metric = "oxygen") *
            lifeSupportMetricRating(diagnosticReportLines, metric = "co2")
}

fun lifeSupportMetricRating(diagnosticReportLines: List<String>, metric: String): Int {
    logDebug(
        "# entering lifeSupportMetricRating() with metric = $metric, " +
                "diagnosticReportLines has ${diagnosticReportLines.size} items"
    )

    // start off by looking at all indexes
    var indexes = diagnosticReportLines.indices.toList()

    var zeroList: MutableList<Int>
    var oneList: MutableList<Int>
    var charPosition = -1

    while (charPosition < diagnosticReportLines[0].length) {
        charPosition++
        logDebug("charPos = $charPosition")

        for (i in indexes) {
            var count0 = 0
            var count1 = 0
            zeroList = mutableListOf()
            oneList = mutableListOf()

            for (j in indexes) {
                val line = diagnosticReportLines[j]
                if (line[charPosition] == '0') {
                    count0++
                    zeroList.add(j)
                } else {
                    count1++
                    oneList.add(j)
                }
            }

            val selectedChar = if (metric == "oxygen") {
                // pick the most-common value
                if (count0 == count1) '=' else if (count0 > count1) '0' else '1'
            } else if (metric == "co2") {
                // pick the least-common value
                if (count0 == count1) '=' else if (count1 > count0) '0' else '1'
            } else {
                ' '
            }
            logDebug("zeroList = $zeroList")
            logDebug("oneList = $oneList")
            val adjective = if (metric == "oxygen") "most" else "least"
            logDebug("$adjective-common char: $selectedChar")

            val indexList = if (selectedChar == '=') {
                if (metric == "co2") zeroList else oneList
            } else {
                if (selectedChar == '0') zeroList else oneList
            }
            logDebug("setting indexes to $indexList")
            if (indexList.size == 1) {
                println(
                    ">> $metric rating: ${diagnosticReportLines[indexList[0]]} (${
                        diagnosticReportLines[indexList[0]].toInt(
                            radix = 2
                        )
                    })"
                )
                return diagnosticReportLines[indexList[0]].toInt(radix = 2)
            } else {
                indexes = indexList
                break
            }
        }
    }
    return -200
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

fun logDebug(s: String) {
    if (debug) {
        println("debug: $s")
    }
}
