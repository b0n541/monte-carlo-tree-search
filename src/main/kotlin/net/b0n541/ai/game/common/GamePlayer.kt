package net.b0n541.ai.game.common

interface GamePlayer<M : GameMove> {

    fun toShortString(): String

    fun play(): M

    fun addMove(move: M)
}