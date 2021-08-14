package net.b0n541.ai.game.connect4.player

import net.b0n541.ai.game.common.GamePlayer
import net.b0n541.ai.game.connect4.Connect4GameState
import net.b0n541.ai.game.connect4.Connect4Move
import net.b0n541.ai.game.connect4.Connect4PlayerSymbol

sealed class Connect4Player : GamePlayer<Connect4PlayerSymbol, Connect4Move> {

    protected lateinit var playerSymbol: Connect4PlayerSymbol
    protected var gameState: Connect4GameState = Connect4GameState()

    override fun startNewGame(playerSymbol: Connect4PlayerSymbol, firstPlayer: Connect4PlayerSymbol) {
        this.playerSymbol = playerSymbol
        gameState = Connect4GameState()
    }

    override fun toShortString(): String {
        return playerSymbol.toString()
    }

    override fun addMove(move: Connect4Move) {
        gameState = gameState.addMove(move)
    }
}