package net.b0n541.ai.tree

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class TreeNodeStatisticsTest {
    @Test
    fun emptyStatistics() {
        val statistics = TreeNodeStatistics(listOf("A", "B"))

        assertThat(statistics.visits).isZero
        assertThat(statistics.getTotalScore("A")).isZero
        assertThat(statistics.getTotalScore("B")).isZero
    }

    @Test
    fun traverseAndAddScores() {
        val statistics = TreeNodeStatistics(listOf("A", "B"))

        statistics.addTraversal()

        assertThat(statistics.visits).isEqualTo(0)
        assertThat(statistics.currentTraversals).isEqualTo(0)
        assertThat(statistics.getTotalScore("A")).isZero
        assertThat(statistics.getTotalScore("B")).isZero

        statistics.addScores(mapOf("A" to 10.0, "B" to 0.0))

        assertThat(statistics.visits).isEqualTo(1)
        assertThat(statistics.currentTraversals).isEqualTo(0)
        assertThat(statistics.getTotalScore("A")).isEqualTo(10.0)
        assertThat(statistics.getTotalScore("B")).isZero

        statistics.addTraversal()

        assertThat(statistics.visits).isEqualTo(1)
        assertThat(statistics.currentTraversals).isEqualTo(0)
        assertThat(statistics.getTotalScore("A")).isEqualTo(10.0)
        assertThat(statistics.getTotalScore("B")).isZero

        statistics.addScores(mapOf("A" to 0.0, "B" to 20.0))

        assertThat(statistics.visits).isEqualTo(2)
        assertThat(statistics.currentTraversals).isEqualTo(0)
        assertThat(statistics.getTotalScore("A")).isEqualTo(10.0)
        assertThat(statistics.getTotalScore("B")).isEqualTo(20.0)
    }

    @Test
    fun noPlayer() {
        assertThrows<IllegalArgumentException> { TreeNodeStatistics(emptyList()) }
    }

    @Test
    fun wrongPlayer() {
        val statistics = TreeNodeStatistics(listOf("A", "B"))

        assertThrows<IllegalArgumentException> { statistics.getTotalScore("C") }
        assertThrows<IllegalArgumentException> { statistics.addScores(mapOf("C" to 123.0)) }
    }
}