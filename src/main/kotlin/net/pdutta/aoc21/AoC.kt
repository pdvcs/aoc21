package net.pdutta.aoc21

import java.io.File

// AoC 2021 - https://adventofcode.com/2021

var debug = false

fun main() {
    day01()
    day02()
    day03()
    day04()
}

fun day04() {
    val gameInput = File("src/main/resources/input04.txt").readText(Charsets.UTF_8)
    println("Problem 7: ${bingoGame(gameInput)}")
    println("Problem 8: ${reverseBingo(gameInput)}")
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

fun day01() {
    val report = File("src/main/resources/input01.txt").readText(Charsets.UTF_8)
    val ms = report.split("\n").map { it.toInt() }
    println("Problem 1: ${measureSweep(ms)}")
    println("Problem 2: ${measureSweepWithSlidingWindow(ms)}")
}
