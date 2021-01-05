package net.b0n541.ai.game.skat

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class CardDeckTest {
    @Test
    fun defaultSort() {
        val cardDeck = CardDeck()

        assertThat(cardDeck.dealCards(PlayerPosition.FOREHAND))
            .containsExactlyInAnyOrder(CA, CK, CQ, CJ, CT, C9, C8, C7, SA, SK)
        assertThat(cardDeck.dealCards(PlayerPosition.MIDDLEHAND))
            .containsExactlyInAnyOrder(SQ, SJ, ST, S9, S8, S7, HA, HK, HQ, HJ)
        assertThat(cardDeck.dealCards(PlayerPosition.REARHAND))
            .containsExactlyInAnyOrder(HT, H9, H8, H7, DA, DK, DQ, DJ, DT, D9)
        assertThat(cardDeck.dealSkat()).containsExactlyInAnyOrder(D8, D7)
    }

    @Test
    fun shuffle() {
        // TODO
    }

    @Test
    fun perfectCardDistribution() {
        val cardDeck = PERFECT_CARD_DISTRIBUTION

        assertThat(cardDeck.dealCards(PlayerPosition.FOREHAND))
            .containsExactlyInAnyOrder(CJ, SJ, HJ, DJ, CA, CT, C9, SA, ST, S9)
        assertThat(cardDeck.dealCards(PlayerPosition.MIDDLEHAND))
            .containsExactlyInAnyOrder(CK, CQ, SK, SQ, HA, HK, HQ, DT, D9, D8)
        assertThat(cardDeck.dealCards(PlayerPosition.REARHAND))
            .containsExactlyInAnyOrder(C8, C7, S8, S7, HT, H9, H8, DA, DK, DQ)
        assertThat(cardDeck.dealSkat()).containsExactlyInAnyOrder(H7, D7)
    }

    @Test
    fun invalidCardDecks() {
        assertThrows<IllegalArgumentException> { CardDeck(setOf(CJ, SJ, HJ, DJ)) }
        assertThrows<IllegalArgumentException> { CardDeck(setOf(CJ, CJ, CJ, CJ)) }
    }
}