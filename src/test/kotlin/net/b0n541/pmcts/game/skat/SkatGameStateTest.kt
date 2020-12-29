package net.b0n541.pmcts.game.skat

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class SkatGameStateTest {
    @Test
    fun gameType() {
        val state = SkatGameState(SkatGameType.CLUBS, PlayerPosition.FOREHAND, PlayerPosition.FOREHAND)

        assertThat(state.gameType).isEqualTo(SkatGameType.CLUBS)
    }

    @Test
    fun nextPlayer() {
        val state = SkatGameState(SkatGameType.CLUBS, PlayerPosition.FOREHAND, PlayerPosition.FOREHAND)

        assertThat(state.nextPlayer).isEqualTo("FOREHAND")
    }

    @Test
    fun playerList() {
        val state = SkatGameState(SkatGameType.CLUBS, PlayerPosition.FOREHAND, PlayerPosition.FOREHAND)

        assertThat(state.players).containsExactlyInAnyOrder(
            PlayerPosition.FOREHAND.toString(),
            PlayerPosition.MIDDLEHAND.toString(),
            PlayerPosition.REARHAND.toString()
        )
    }

    @Test
    fun addMoves() {
        val state0 = SkatGameState(SkatGameType.CLUBS, PlayerPosition.FOREHAND, PlayerPosition.FOREHAND)
        val state1 = state0.addMove(SkatGameMove(PlayerPosition.FOREHAND, Card.HJ))
        val state2 = state1.addMove(SkatGameMove(PlayerPosition.MIDDLEHAND, Card.CJ))
        val state3 = state2.addMove(SkatGameMove(PlayerPosition.REARHAND, Card.SJ))

        Assertions.assertThrows(NoSuchElementException::class.java) { state0.lastMove }
        assertThat(state0.nextPlayer).isEqualTo("FOREHAND")
        assertThat(state0.isGameFinished).isFalse

        assertThat(state1.lastMove).isEqualTo(SkatGameMove(PlayerPosition.FOREHAND, Card.HJ))
        assertThat(state1.nextPlayer).isEqualTo("MIDDLEHAND")
        assertThat(state1.isGameFinished).isFalse

        assertThat(state2.lastMove).isEqualTo(SkatGameMove(PlayerPosition.MIDDLEHAND, Card.CJ))
        assertThat(state2.nextPlayer).isEqualTo("REARHAND")
        assertThat(state2.isGameFinished).isFalse

        assertThat(state3.lastMove).isEqualTo(SkatGameMove(PlayerPosition.REARHAND, Card.SJ))
        assertThat(state3.nextPlayer).isEqualTo("MIDDLEHAND")
        assertThat(state3.isGameFinished).isFalse
    }

    @Test
    fun finishGame() {

        val cardDeck = mutableSetOf<Card>()
        cardDeck.addAll(Card.values())

        var state = SkatGameState(SkatGameType.CLUBS, PlayerPosition.FOREHAND, PlayerPosition.FOREHAND)

        for (i in 1..10) {
            val trickForeHand = state.nextPlayerPosition
            state = state.addMove(SkatGameMove(trickForeHand, randomCard(cardDeck)))
                .addMove(SkatGameMove(trickForeHand.nextPlayer, randomCard(cardDeck)))
                .addMove(SkatGameMove(trickForeHand.nextPlayer.nextPlayer, randomCard(cardDeck)))
        }

        assertThat(state.isGameFinished).isTrue

        assertThat(state.gameValues.maxOfOrNull { it.value }).isLessThanOrEqualTo(120.0)
    }

    private fun randomCard(cardDeck: MutableSet<Card>): Card {
        val card = cardDeck.random()
        cardDeck.remove(card)
        return card
    }
}