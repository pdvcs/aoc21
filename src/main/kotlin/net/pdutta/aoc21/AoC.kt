package net.pdutta.aoc21

import java.io.File

// AoC 2021 - https://adventofcode.com/2021

fun main() {
    val report = File("src/main/resources/input01.txt").readText(Charsets.UTF_8)
    val ms = toList(report, "\n")
    println(largerThanPrevious(ms))
    println(slidingWindow(ms))
}

// Problem 2
fun slidingWindow(ms: List<Int>): Int {
    var w = mutableListOf<Int>()
    for (i in 0 until ms.size - 2) {
        w.add(ms[i] + ms[i + 1] + ms[i + 2])
    }
    return largerThanPrevious(w)
}

// Problem 1
fun largerThanPrevious(ms: List<Int>): Int {
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

fun toList(s: String, delimiter: String = " "): List<Int> {
    return s.split(delimiter).map { it.toInt() }
}
