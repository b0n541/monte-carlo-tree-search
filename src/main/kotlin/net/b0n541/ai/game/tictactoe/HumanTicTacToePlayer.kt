package net.b0n541.ai.game.tictactoe

import java.util.*

class HumanTicTacToePlayer(playerSymbol: PlayerSymbol, firstPlayer: PlayerSymbol) :
    TicTacToePlayer(playerSymbol, firstPlayer) {
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