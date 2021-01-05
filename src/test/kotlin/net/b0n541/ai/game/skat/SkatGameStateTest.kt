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
        val state1 = state0.addMove(SkatGameMove(PlayerPosition.FOREHAND, HJ))
        val state2 = state1.addMove(SkatGameMove(PlayerPosition.MIDDLEHAND, CJ))
        val state3 = state2.addMove(SkatGameMove(PlayerPosition.REARHAND, SJ))

        Assertions.assertThrows(NoSuchElementException::class.java) { state0.lastMove }
        assertThat(state0.nextPlayer).isEqualTo("FOREHAND")
        assertThat(state0.isGameFinished).isFalse

        assertThat(state1.lastMove).isEqualTo(SkatGameMove(PlayerPosition.FOREHAND, HJ))
        assertThat(state1.nextPlayer).isEqualTo("MIDDLEHAND")
        assertThat(state1.isGameFinished).isFalse

        assertThat(state2.lastMove).isEqualTo(SkatGameMove(PlayerPosition.MIDDLEHAND, CJ))
        assertThat(state2.nextPlayer).isEqualTo("REARHAND")
        assertThat(state2.isGameFinished).isFalse

        assertThat(state3.lastMove).isEqualTo(SkatGameMove(PlayerPosition.REARHAND, SJ))
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
            state = state.addMove(SkatGameMove(trickForeHand, randomCard(unplayedCards)))
                .addMove(SkatGameMove(trickForeHand.nextPlayer, randomCard(unplayedCards)))
                .addMove(SkatGameMove(trickForeHand.nextPlayer.nextPlayer, randomCard(unplayedCards)))
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
            SkatGameMove(PlayerPosition.FOREHAND, CJ),
            SkatGameMove(PlayerPosition.FOREHAND, SJ),
            SkatGameMove(PlayerPosition.FOREHAND, HJ),
            SkatGameMove(PlayerPosition.FOREHAND, DJ),
            SkatGameMove(PlayerPosition.FOREHAND, CA),
            SkatGameMove(PlayerPosition.FOREHAND, CT),
            SkatGameMove(PlayerPosition.FOREHAND, C9),
            SkatGameMove(PlayerPosition.FOREHAND, SA),
            SkatGameMove(PlayerPosition.FOREHAND, ST),
            SkatGameMove(PlayerPosition.FOREHAND, S9)
        )

        state = state.addMove(SkatGameMove(PlayerPosition.FOREHAND, CJ))

        assertThat(state.possibleMoves).containsExactlyInAnyOrder(
            SkatGameMove(PlayerPosition.MIDDLEHAND, CK),
            SkatGameMove(PlayerPosition.MIDDLEHAND, CQ)
        )
    }
}