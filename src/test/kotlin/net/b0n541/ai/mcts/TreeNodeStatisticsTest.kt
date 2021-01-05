package net.b0n541.ai.mcts

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class TreeNodeStatisticsTest {
    @Test
    fun emptyStatistics() {
        val statistics = TreeNodeStatistics(listOf("A", "B"))
        Assertions.assertThat(statistics.visits).isZero
        Assertions.assertThat(statistics.getTotalScore("A")).isZero
        Assertions.assertThat(statistics.getTotalScore("B")).isZero
    }


    @Test
    fun addScores() {
        val statistics = TreeNodeStatistics(listOf("A", "B"))
        statistics.addScores(mapOf("A" to 10.0, "B" to 0.0))
        Assertions.assertThat(statistics.visits).isEqualTo(1)
        Assertions.assertThat(statistics.getTotalScore("A")).isEqualTo(10.0)
        Assertions.assertThat(statistics.getTotalScore("B")).isZero
        statistics.addScores(mapOf("A" to 0.0, "B" to 20.0))
        Assertions.assertThat(statistics.visits).isEqualTo(2)
        Assertions.assertThat(statistics.getTotalScore("A")).isEqualTo(10.0)
        Assertions.assertThat(statistics.getTotalScore("B")).isEqualTo(20.0)
    }

    @Test
    fun noPlayer() {
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException::class.java) {
            TreeNodeStatistics(
                emptyList()
            )
        }
    }

    @Test
    fun wrongPlayer() {
        val statistics = TreeNodeStatistics(listOf("A", "B"))
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException::class.java) { statistics.getTotalScore("C") }
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException::class.java) {
            statistics.addScores(mapOf("C" to 123.0))
        }
    }
}