package net.b0n541.ai.mcts

class RandomPlayoutPolicy : PlayoutPolicy {

    override fun play(gameState: GameState<GameMove>) = gameState.possibleMoves.random()
}