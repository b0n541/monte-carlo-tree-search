package net.b0n541.ai

import net.b0n541.ai.game.tictactoe.player.MctsTicTacToePlayer
import net.b0n541.ai.game.tictactoe.player.RandomTicTacToePlayer
import net.b0n541.ai.tournament.TicTacToeTournament
import org.slf4j.LoggerFactory

object App {
    private val LOG = LoggerFactory.getLogger(App::class.java)

    @JvmStatic
    fun main(args: Array<String>) {
        //TicTacToe.playGame()
        //Connect4.playGame()
        //Skat.playGame()

        val tournament = TicTacToeTournament(listOf(RandomTicTacToePlayer(), MctsTicTacToePlayer()))

        tournament.run()

        println(tournament.getScores())
    }
}