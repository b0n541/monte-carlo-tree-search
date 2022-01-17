package net.b0n541.ai.game.schwimmen

import net.b0n541.ai.game.common.card.CardDeck
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class SchwimmenGameStateTest {
    @Test
    fun initGame() {
        val state = SchwimmenGameState(SchwimmenPlayerSymbol.ONE)

        assertThat(state.nextPlayerPosition).isEqualTo(SchwimmenPlayerSymbol.ONE)
        assertThat(state.isGameFinished).isFalse()
    }

    @Test
    fun addMovesWithoutCardSwap() {
        val state = SchwimmenGameState(SchwimmenPlayerSymbol.ONE)

        dealCards(state)

        var newState = state.addMove(SchwimmenMove(SchwimmenPlayerSymbol.ONE, SchwimmenMoveType.CLOSE))

        assertThat(newState.nextPlayer).isEqualTo("TWO")

        newState = newState.addMove(SchwimmenMove(SchwimmenPlayerSymbol.TWO, SchwimmenMoveType.SHOVE))

        assertThat(newState.nextPlayer).isEqualTo("THREE")

        newState = newState.addMove(SchwimmenMove(SchwimmenPlayerSymbol.THREE, SchwimmenMoveType.SHOVE))

        assertThat(newState.nextPlayer).isEqualTo("ONE")
    }

    private fun dealCards(state: SchwimmenGameState) {
        val cardDeck = CardDeck()

        state.dealPlayerCards(SchwimmenPlayerSymbol.ONE, cardDeck.dealRandomCards(3))
        state.dealPlayerCards(SchwimmenPlayerSymbol.TWO, cardDeck.dealRandomCards(3))
        state.dealPlayerCards(SchwimmenPlayerSymbol.THREE, cardDeck.dealRandomCards(3))
        state.dealMiddleCards(cardDeck.dealRandomCards(3))
    }
}