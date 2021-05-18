package net.b0n541.ai.algorithm.mcts

import net.b0n541.ai.game.common.GameMove
import net.b0n541.ai.game.common.GameState

interface PlayoutPolicy {
    fun play(gameState: GameState<GameMove>): GameMove
}