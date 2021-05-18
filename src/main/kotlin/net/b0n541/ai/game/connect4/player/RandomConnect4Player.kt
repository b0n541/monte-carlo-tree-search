package net.b0n541.ai.game.connect4.player

import net.b0n541.ai.game.connect4.Connect4Move

class RandomConnect4Player : Connect4Player() {

    override fun play(): Connect4Move {
        return gameState.possibleMoves.random()
    }
}