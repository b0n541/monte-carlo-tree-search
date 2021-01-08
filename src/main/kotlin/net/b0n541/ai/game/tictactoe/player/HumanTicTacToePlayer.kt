package net.b0n541.ai.game.tictactoe.player

import net.b0n541.ai.game.tictactoe.PlayerSymbol
import net.b0n541.ai.game.tictactoe.TicTacToeMove
import java.util.*

class HumanTicTacToePlayer(playerSymbol: PlayerSymbol, firstPlayer: PlayerSymbol) :
    AbstractTicTacToePlayer(playerSymbol, firstPlayer) {
    
    override fun play(): TicTacToeMove {
        println("Your move...")
        print("Row: ")
        val scanner = Scanner(System.`in`)
        val row = scanner.nextInt()
        print("Column: ")
        val column = scanner.nextInt()
        return TicTacToeMove(playerSymbol, row, column)
    }
}