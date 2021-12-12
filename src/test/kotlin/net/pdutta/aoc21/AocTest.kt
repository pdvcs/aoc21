package net.pdutta.aoc21

import kotlin.test.Test
import kotlin.test.assertEquals

internal class AoCTest {
    private val report = "199 200 208 210 200 207 240 269 260 263"

    @Test
    fun testProblem01() {
        val expected = 7
        assertEquals(expected, largerThanPrevious(toList(report)))
    }

    @Test
    fun testProblem02() {
        val expected = 5
        assertEquals(expected, slidingWindow(toList(report)))
    }
}
