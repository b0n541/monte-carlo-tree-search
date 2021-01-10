package net.b0n541.ai

import net.b0n541.ai.game.connect4.Connect4
import org.slf4j.LoggerFactory

object App {
    private val LOG = LoggerFactory.getLogger(App::class.java)

    @JvmStatic
    fun main(args: Array<String>) {
        //TicTacToe.playGame()
        Connect4.playGame()
        //Skat.playGame()
    }
}