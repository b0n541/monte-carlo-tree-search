package net.b0n541.ai.game.connect4.player

import net.b0n541.ai.game.connect4.Connect4Move
import net.b0n541.ai.game.connect4.Connect4PlayerSymbol

class AlphaZeroConnect4Player(playerSymbol: Connect4PlayerSymbol, firstPlayer: Connect4PlayerSymbol) :
    AbstractConnect4Player(playerSymbol, firstPlayer) {

    override fun play(): Connect4Move {
        return gameState.possibleMoves.random()
    }
}