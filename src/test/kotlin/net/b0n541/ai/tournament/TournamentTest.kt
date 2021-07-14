package net.b0n541.ai.tournament

import net.b0n541.ai.game.connect4.player.HumanConnect4Player
import net.b0n541.ai.game.connect4.player.MctsConnect4Player
import net.b0n541.ai.game.connect4.player.RandomConnect4Player
import net.b0n541.ai.game.tictactoe.player.MctsTicTacToePlayer
import net.b0n541.ai.game.tictactoe.player.RandomTicTacToePlayer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class TournamentTest {
    @Test
    fun `2 player types in 2 player game`() {
        val first = RandomTicTacToePlayer()
        val second = MctsTicTacToePlayer()

        val result = getPlayerCombinations(2, listOf(first, second))

        assertThat(result).containsExactlyInAnyOrder(
            listOf(first, second),
            listOf(second, first)
        )
    }

    @Test
    fun `3 player types in 2 player game`() {
        val first = RandomConnect4Player()
        val second = MctsConnect4Player()
        val third = HumanConnect4Player()

        val result = getPlayerCombinations(2, listOf(first, second, third))

        assertThat(result).containsExactlyInAnyOrder(
            listOf(first, second),
            listOf(first, third),
            listOf(second, first),
            listOf(second, third),
            listOf(third, first),
            listOf(third, second)
        )
    }

    @Test
    fun `2 player types in 3 player game`() {
        val first = RandomConnect4Player()
        val second = MctsConnect4Player()

        val result = getPlayerCombinations(3, listOf(first, second))

        assertThat(result).containsExactlyInAnyOrder(
            listOf(first, first, second),
            listOf(first, second, first),
            listOf(first, second, second),
            listOf(second, second, first),
            listOf(second, first, second),
            listOf(second, first, first)
        )
    }

    @Test
    fun `3 player types in 3 player game`() {
        val first = RandomConnect4Player()
        val second = MctsConnect4Player()
        val third = HumanConnect4Player()

        val result = getPlayerCombinations(3, listOf(first, second, third))

        assertThat(result).containsExactlyInAnyOrder(
            listOf(first, first, second),
            listOf(first, first, third),
            listOf(first, second, first),
            listOf(first, second, second),
            listOf(first, third, first),
            listOf(first, third, third),
            listOf(first, second, third),
            listOf(first, third, second),
            listOf(second, second, first),
            listOf(second, second, third),
            listOf(second, first, second),
            listOf(second, first, first),
            listOf(second, third, second),
            listOf(second, third, third),
            listOf(second, first, third),
            listOf(second, third, first),
            listOf(third, third, first),
            listOf(third, third, second),
            listOf(third, first, third),
            listOf(third, first, first),
            listOf(third, second, third),
            listOf(third, second, second),
            listOf(third, first, second),
            listOf(third, second, first)
        )
    }
}