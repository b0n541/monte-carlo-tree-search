package net.b0n541.ai.game.connect4.player

import net.b0n541.ai.game.connect4.Connect4GameState
import net.b0n541.ai.game.connect4.Connect4Move
import net.b0n541.ai.game.connect4.Connect4PlayerSymbol
import net.b0n541.ai.mcts.GamePlayer

abstract class AbstractConnect4Player(
    val playerSymbol: Connect4PlayerSymbol,
    firstPlayer: Connect4PlayerSymbol
) :
    GamePlayer<Connect4Move> {

    protected var gameState = Connect4GameState(firstPlayer = firstPlayer)

    override fun toShortString(): String {
        return playerSymbol.toString()
    }

    override fun addMove(move: Connect4Move) {
        gameState = gameState.addMove(move)
    }
}