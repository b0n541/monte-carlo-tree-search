package net.b0n541.ai.game.skat

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
        val state1 = state0.addMove(SkatMove(PlayerPosition.FOREHAND, HJ))
        val state2 = state1.addMove(SkatMove(PlayerPosition.MIDDLEHAND, CJ))
        val state3 = state2.addMove(SkatMove(PlayerPosition.REARHAND, SJ))

        Assertions.assertThrows(NoSuchElementException::class.java) { state0.lastMove }
        assertThat(state0.nextPlayer).isEqualTo("FOREHAND")
        assertThat(state0.isGameFinished).isFalse

        assertThat(state1.lastMove).isEqualTo(SkatMove(PlayerPosition.FOREHAND, HJ))
        assertThat(state1.nextPlayer).isEqualTo("MIDDLEHAND")
        assertThat(state1.isGameFinished).isFalse

        assertThat(state2.lastMove).isEqualTo(SkatMove(PlayerPosition.MIDDLEHAND, CJ))
        assertThat(state2.nextPlayer).isEqualTo("REARHAND")
        assertThat(state2.isGameFinished).isFalse

        assertThat(state3.lastMove).isEqualTo(SkatMove(PlayerPosition.REARHAND, SJ))
        assertThat(state3.nextPlayer).isEqualTo("MIDDLEHAND")
        assertThat(state3.isGameFinished).isFalse
    }

    @Test
    fun finishGame() {
        var state = SkatGameState(SkatGameType.CLUBS, PlayerPosition.FOREHAND, PlayerPosition.FOREHAND)

        val cards = CardDeck()
        cards.shuffle()
        val unplayedCards = cards.sortedCards.toMutableList()

        for (i in 1..10) {
            val trickForeHand = state.nextPlayerPosition
            state = state.addMove(SkatMove(trickForeHand, randomCard(unplayedCards)))
                .addMove(SkatMove(trickForeHand.nextPlayer, randomCard(unplayedCards)))
                .addMove(SkatMove(trickForeHand.nextPlayer.nextPlayer, randomCard(unplayedCards)))
        }

        assertThat(state.isGameFinished).isTrue

        assertThat(state.gameValues.maxOfOrNull { it.value }).isLessThanOrEqualTo(120.0)
        assertThat(state.gameValues.map { it.value }.sum()).isGreaterThanOrEqualTo(120.0)
    }

    private fun randomCard(cardDeck: MutableList<OpenCard>): OpenCard {
        val card = cardDeck.random()
        cardDeck.remove(card)
        return card
    }

    @Test
    fun getPossibleMoves() {
        val cards = PERFECT_CARD_DISTRIBUTION
        var state = SkatGameState(SkatGameType.CLUBS, PlayerPosition.FOREHAND, PlayerPosition.FOREHAND)

        state.dealPlayerCards(PlayerPosition.FOREHAND, cards.dealCards(PlayerPosition.FOREHAND).toList())
        state.dealPlayerCards(PlayerPosition.MIDDLEHAND, cards.dealCards(PlayerPosition.MIDDLEHAND).toList())
        state.dealPlayerCards(PlayerPosition.REARHAND, cards.dealCards(PlayerPosition.REARHAND).toList())
        state.dealSkat(cards.dealSkat().toList())

        assertThat(state.possibleMoves).containsExactlyInAnyOrder(
            SkatMove(PlayerPosition.FOREHAND, CJ),
            SkatMove(PlayerPosition.FOREHAND, SJ),
            SkatMove(PlayerPosition.FOREHAND, HJ),
            SkatMove(PlayerPosition.FOREHAND, DJ),
            SkatMove(PlayerPosition.FOREHAND, CA),
            SkatMove(PlayerPosition.FOREHAND, CT),
            SkatMove(PlayerPosition.FOREHAND, C9),
            SkatMove(PlayerPosition.FOREHAND, SA),
            SkatMove(PlayerPosition.FOREHAND, ST),
            SkatMove(PlayerPosition.FOREHAND, S9)
        )

        state = state.addMove(SkatMove(PlayerPosition.FOREHAND, CJ))

        assertThat(state.possibleMoves).containsExactlyInAnyOrder(
            SkatMove(PlayerPosition.MIDDLEHAND, CK),
            SkatMove(PlayerPosition.MIDDLEHAND, CQ)
        )
    }
}