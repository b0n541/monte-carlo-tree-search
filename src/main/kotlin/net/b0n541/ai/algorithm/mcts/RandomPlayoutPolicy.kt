package net.b0n541.ai.algorithm.mcts

import net.b0n541.ai.game.common.GameMove
import net.b0n541.ai.game.common.GameState

class RandomPlayoutPolicy : PlayoutPolicy {

    override fun play(gameState: GameState<GameMove>) = gameState.possibleMoves.random()
}