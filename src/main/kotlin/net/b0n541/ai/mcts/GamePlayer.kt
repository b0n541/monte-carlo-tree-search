package net.b0n541.ai.mcts

interface GamePlayer<M : GameMove> {
    fun toShortString(): String

    fun addMove(move: M)
}