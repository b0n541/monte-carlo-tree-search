package net.b0n541.ai.game.connect4.player.random

import net.b0n541.ai.game.connect4.Connect4Move
import net.b0n541.ai.game.connect4.Connect4PlayerSymbol
import net.b0n541.ai.game.connect4.player.AbstractConnect4Player

class RandomConnect4Player(playerSymbol: Connect4PlayerSymbol, firstPlayer: Connect4PlayerSymbol) :
    AbstractConnect4Player(playerSymbol, firstPlayer) {

    override fun play(): Connect4Move {
        return gameState.possibleMoves.random()
    }
}