package net.b0n541.ai.game.connect4.player

import net.b0n541.ai.game.connect4.Connect4Move
import java.util.*

class HumanConnect4Player : Connect4Player() {

    override fun play(): Connect4Move {
        println("Your move...")
        print("Column: ")
        val column = Scanner(System.`in`).nextInt()
        return Connect4Move(playerSymbol, column)
    }
}