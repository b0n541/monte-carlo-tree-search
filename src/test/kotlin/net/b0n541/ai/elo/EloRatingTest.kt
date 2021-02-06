package net.b0n541.ai.elo

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.within
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class EloRatingTest {

    companion object {
        @JvmStatic
        private fun winPropabilities() = listOf(
            Arguments.of(1000, 1000, 0.5),
            Arguments.of(1100, 1000, 0.64),
            Arguments.of(900, 1000, 0.36),
            Arguments.of(1200, 1000, 0.76),
            Arguments.of(800, 1000, 0.24),
            Arguments.of(1300, 1000, 0.85),
            Arguments.of(700, 1000, 0.15),
            Arguments.of(1400, 1000, 0.91),
            Arguments.of(600, 1000, 0.09)
        )
    }

    @ParameterizedTest
    @MethodSource("winPropabilities")
    fun winProbability(eloPlayerA: Int, eloPlayerB: Int, expectedWinProbability: Double) {
        assertThat(EloRating.winPropability(eloPlayerA, eloPlayerB))
            .isCloseTo(expectedWinProbability, within(0.001))
    }

    @Test
    fun lowerRating() {
        val playerARating = 1613
        val gameResults = listOf(
            1609 to 0.0,
            1477 to 0.5,
            1388 to 1.0,
            1586 to 1.0,
            1720 to 0.0
        )
        assertThat(EloRating.newRating(playerARating, gameResults))
            .isEqualTo(1601)
    }

    @Test
    fun raiseRating() {
        val playerARating = 1613
        val gameResults = listOf(
            1609 to 1.0,
            1477 to 1.0,
            1388 to 0.0,
            1586 to 0.5,
            1720 to 0.5
        )
        assertThat(EloRating.newRating(playerARating, gameResults))
            .isEqualTo(1617)
    }
}