package net.pdutta.aoc21

import java.io.File

// AoC 2021 - https://adventofcode.com/2021

fun main() {
    day01To03()
    day04()
    day05()
}

fun day05() {
    val p = Day05Vents()

    val ventInput = File("src/main/resources/input05.txt").readText(Charsets.UTF_8).split("\n")
    println("Problem 9: ${p.overlappingVents(ventInput, countDiagonals = false)}")
    println("Problem 10: ${p.overlappingVents(ventInput, countDiagonals = true)}")
}

fun day04() {
    val p = Day04Bingo()

    val bingoInput = File("src/main/resources/input04.txt").readText(Charsets.UTF_8)
    println("Problem 7: ${p.bingoGame(bingoInput)}")
    println("Problem 8: ${p.reverseBingo(bingoInput)}")
}

fun day01To03() {
    val p = Day01To03()

    val day1input = File("src/main/resources/input01.txt").readText(Charsets.UTF_8).split("\n").map { it.toInt() }
    println("Problem 1: ${p.measureSweep(day1input)}")
    println("Problem 2: ${p.measureSweepWithSlidingWindow(day1input)}")

    val day2input = File("src/main/resources/input02.txt").readText(Charsets.UTF_8).split("\n")
    println("Problem 3: ${p.diveCalc(day2input)}")
    println("Problem 4: ${p.diveCalcWithAim(day2input)}")

    val day3input = File("src/main/resources/input03.txt").readText(Charsets.UTF_8).split("\n")
    println("Problem 5: ${p.binaryDiagnostic(day3input)}")
    println("Problem 6: ${p.lifeSupportDiagnostic(day3input)}")
}
