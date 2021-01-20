package net.b0n541.ai.mcts

interface PlayoutPolicy {
    fun play(gameState: GameState<GameMove>): GameMove
}