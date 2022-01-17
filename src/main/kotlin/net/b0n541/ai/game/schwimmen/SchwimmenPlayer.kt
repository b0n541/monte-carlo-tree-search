package net.b0n541.ai.game.schwimmen

import net.b0n541.ai.game.common.GamePlayer
import net.b0n541.ai.game.common.card.Hand

class SchwimmenPlayer : GamePlayer<SchwimmenPlayerSymbol, SchwimmenMove> {

    private lateinit var playerSymbol: SchwimmenPlayerSymbol
    private lateinit var firstPlayer: SchwimmenPlayerSymbol
    private var hand: Hand = Hand()

    override fun startNewGame(playerSymbol: SchwimmenPlayerSymbol, firstPlayer: SchwimmenPlayerSymbol) {
        this.playerSymbol = playerSymbol
        this.firstPlayer = firstPlayer
    }

    override fun play(): SchwimmenMove {
        TODO("Not yet implemented")
    }

    override fun addMove(move: SchwimmenMove) {
        TODO("Not yet implemented")
    }

    override fun toShortString(): String {
        TODO("Not yet implemented")
    }
}