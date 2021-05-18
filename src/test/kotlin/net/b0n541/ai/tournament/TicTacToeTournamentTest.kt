package net.b0n541.ai.tournament

import net.b0n541.ai.game.tictactoe.player.HumanTicTacToePlayer
import net.b0n541.ai.game.tictactoe.player.MctsTicTacToePlayer
import net.b0n541.ai.game.tictactoe.player.RandomTicTacToePlayer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

internal class TicTacToeTournamentTest {
    @Test
    @Disabled
    fun `Player permutations`() {
        assertThat(
            permutations(listOf(RandomTicTacToePlayer(), MctsTicTacToePlayer(), HumanTicTacToePlayer()), 2)
        ).containsAll(
            listOf(
                listOf(RandomTicTacToePlayer(), MctsTicTacToePlayer()),
                listOf(RandomTicTacToePlayer(), HumanTicTacToePlayer()),
                listOf(MctsTicTacToePlayer(), HumanTicTacToePlayer()),
                listOf(MctsTicTacToePlayer(), RandomTicTacToePlayer()),
                listOf(HumanTicTacToePlayer(), RandomTicTacToePlayer()),
                listOf(HumanTicTacToePlayer(), MctsTicTacToePlayer())
            )
        )
    }
}