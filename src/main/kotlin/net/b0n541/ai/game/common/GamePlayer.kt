package net.b0n541.ai.game.common

interface GamePlayer<S : PlayerSymbol, M : GameMove> {

    fun startNewGame(playerSymbol: S, firstPlayer: S)

    fun play(): M

    fun addMove(move: M)

    fun toShortString(): String
}