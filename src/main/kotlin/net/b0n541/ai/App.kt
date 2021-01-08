package net.b0n541.ai

import net.b0n541.ai.game.tictactoe.TicTacToe.playGame
import org.slf4j.LoggerFactory

object App {
    private val LOG = LoggerFactory.getLogger(App::class.java)

    @JvmStatic
    fun main(args: Array<String>) {
        playGame()
        //Skat.playGame()
    }
}