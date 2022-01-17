package net.b0n541.ai

import net.b0n541.ai.game.schwimmen.Schwimmen
import org.slf4j.LoggerFactory

object App {
    private val LOG = LoggerFactory.getLogger(App::class.java)

    @JvmStatic
    fun main(args: Array<String>) {
//        TicTacToe(noughtsPlayer = RandomTicTacToePlayer(), crossesPlayer = MctsTicTacToePlayer()).play()
//        Connect4(noughtsPlayer = RandomConnect4Player(), crossesPlayer = MctsConnect4Player()).play()
        //Skat.playGame()

//        val tournament = TicTacToeTournament(listOf(RandomTicTacToePlayer(), MctsTicTacToePlayer()))
//        tournament.run()
//        LOG.info("Final ranking:")
//        println(tournament.getScores())

        Schwimmen.play()
    }
}