package net.b0n541.ai.tournament.elo

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.within
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class EloRatingTest {
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class ExpectedScores {
        private fun expectedScores() = listOf(
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

        @ParameterizedTest
        @MethodSource("expectedScores")
        fun `Expected scores`(eloPlayerA: Int, eloPlayerB: Int, expectedWinProbability: Double) {
            assertThat(expectedScore(eloPlayerA, eloPlayerB))
                .isCloseTo(expectedWinProbability, within(0.001))
        }
    }

    private val gameResultsForLowerRating = listOf(
        1609 to 0.0,
        1477 to 0.5,
        1388 to 1.0,
        1586 to 1.0,
        1720 to 0.0
    )

    @Test
    fun `Lower rating after batch of game`() {
        assertThat(
            EloRating(rating = 1613).addGames(gameResultsForLowerRating).rating
        ).isEqualTo(1601)
    }

    @Test
    fun `Lower rating after single game`() {
        var eloRating = EloRating(rating = 1613)
        gameResultsForLowerRating.forEach { eloRating = eloRating.addGame(it.first, it.second) }

        assertThat(eloRating.rating).isEqualTo(1604)
    }

    private val gameResultsForHigherRating = listOf(
        1609 to 1.0,
        1477 to 1.0,
        1388 to 0.0,
        1586 to 0.5,
        1720 to 0.5
    )

    @Test
    fun `Higher rating after batch of games`() {
        assertThat(
            EloRating(rating = 1613).addGames(gameResultsForHigherRating).rating
        ).isEqualTo(1617)
    }

    @Test
    fun `Higher rating after single games`() {
        var eloRating = EloRating(rating = 1613)
        gameResultsForHigherRating.forEach { eloRating = eloRating.addGame(it.first, it.second) }

        assertThat(eloRating.rating).isEqualTo(1616)
    }
}