package net.b0n541.pmcts.game.skat

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class HandTest {
    @Test
    fun emptyHand() {
        val hand = Hand()

        assertThat(hand.cards).isEmpty()
        assertThat(hand.cardValues).isEqualTo(0)
        assertThat(hand.containsHiddenCards).isFalse
    }

    @Test
    fun takeOpenCards() {
        val hand = Hand()

        hand.takeCards(listOf(CJ, SJ, HJ))

        assertThat(hand.cards).containsExactlyInAnyOrder(CJ, SJ, HJ)
        assertThat(hand.cardValues).isEqualTo(6)
        assertThat(hand.containsHiddenCards).isFalse
    }

    @Test
    fun takeHiddenCards() {
        val hand = Hand(listOf(HiddenCard, HiddenCard))

        assertThat(hand.cards).hasSize(2)
        assertThat(hand.cardValues).isEqualTo(0)
        assertThat(hand.containsHiddenCards).isTrue
    }

    @Test
    fun removeOpenCards() {
        val hand = Hand(listOf(CJ, SJ, HJ))

        hand.removeCard(CJ)

        assertThat(hand.cards).containsExactlyInAnyOrder(SJ, HJ)
        assertThat(hand.cardValues).isEqualTo(4)
        assertThat(hand.containsHiddenCards).isFalse
    }

    @Test
    fun removeHiddenCards() {
        val hand = Hand(listOf(HiddenCard, HiddenCard))

        hand.removeCard(HiddenCard)

        assertThat(hand.cards).containsExactlyInAnyOrder(HiddenCard)
        assertThat(hand.cardValues).isEqualTo(0)
        assertThat(hand.containsHiddenCards).isTrue
    }

    @Test
    fun mixedOpenAndHiddenCards() {
        val hand = Hand()

        hand.takeCards(listOf(CJ, SJ, HiddenCard, HiddenCard))

        assertThat(hand.cards).containsExactlyInAnyOrder(CJ, SJ, HiddenCard, HiddenCard)
        assertThat(hand.cardValues).isEqualTo(4)
        assertThat(hand.containsHiddenCards).isTrue

        hand.removeCard(HiddenCard)

        assertThat(hand.cards).containsExactlyInAnyOrder(CJ, SJ, HiddenCard)
        assertThat(hand.cardValues).isEqualTo(4)
        assertThat(hand.containsHiddenCards).isTrue

        hand.removeCard(CJ)

        assertThat(hand.cards).containsExactlyInAnyOrder(SJ, HiddenCard)
        assertThat(hand.cardValues).isEqualTo(2)
        assertThat(hand.containsHiddenCards).isTrue

        hand.removeCard(HiddenCard)

        assertThat(hand.cards).containsExactlyInAnyOrder(SJ)
        assertThat(hand.cardValues).isEqualTo(2)
        assertThat(hand.containsHiddenCards).isFalse
    }
}