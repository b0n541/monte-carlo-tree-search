package net.b0n541.ai.game.connect4.player.human

import net.b0n541.ai.game.connect4.Connect4Move
import net.b0n541.ai.game.connect4.Connect4PlayerSymbol
import net.b0n541.ai.game.connect4.player.AbstractConnect4Player
import java.util.*

class HumanConnect4Player(playerSymbol: Connect4PlayerSymbol, firstPlayer: Connect4PlayerSymbol) :
    AbstractConnect4Player(playerSymbol, firstPlayer) {

    override fun play(): Connect4Move {
        println("Your move...")
        print("Column: ")
        val column = Scanner(System.`in`).nextInt()
        return Connect4Move(playerSymbol, column)
    }
}