package net.b0n541.ai.game.skat

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class HandTest {
    @Test
    fun emptyHand() {
        val hand = Hand()

        assertThat(hand.cards).isEmpty()
        assertThat(hand.cardValues).isEqualTo(0)
        assertThat(hand.holdsHiddenCards).isFalse
        assertThat(hand.openCards).isEmpty()
    }

    @Test
    fun takeOpenCards() {
        val hand = Hand()

        hand.takeCards(listOf(CJ, SJ, HJ))

        assertThat(hand.cards).containsExactlyInAnyOrder(CJ, SJ, HJ)
        assertThat(hand.cardValues).isEqualTo(6)
        assertThat(hand.holdsHiddenCards).isFalse
        assertThat(hand.openCards).containsExactlyInAnyOrder(CJ, SJ, HJ)
    }

    @Test
    fun takeHiddenCards() {
        val hand = Hand(listOf(HiddenCard, HiddenCard))

        assertThat(hand.cards).hasSize(2)
        assertThat(hand.cardValues).isEqualTo(0)
        assertThat(hand.holdsHiddenCards).isTrue
        assertThat(hand.openCards).isEmpty()
    }

    @Test
    fun removeOpenCards() {
        val hand = Hand(listOf(CJ, SJ, HJ))

        hand.removeCard(CJ)

        assertThat(hand.cards).containsExactlyInAnyOrder(SJ, HJ)
        assertThat(hand.cardValues).isEqualTo(4)
        assertThat(hand.holdsHiddenCards).isFalse
        assertThat(hand.openCards).containsExactlyInAnyOrder(SJ, HJ)
    }

    @Test
    fun removeHiddenCards() {
        val hand = Hand(listOf(HiddenCard, HiddenCard))

        hand.removeCard(HiddenCard)

        assertThat(hand.cards).containsExactlyInAnyOrder(HiddenCard)
        assertThat(hand.cardValues).isEqualTo(0)
        assertThat(hand.holdsHiddenCards).isTrue
        assertThat(hand.openCards).isEmpty()
    }

    @Test
    fun mixedOpenAndHiddenCards() {
        val hand = Hand()

        hand.takeCards(listOf(CJ, SJ, HiddenCard, HiddenCard))

        assertThat(hand.cards).containsExactlyInAnyOrder(CJ, SJ, HiddenCard, HiddenCard)
        assertThat(hand.cardValues).isEqualTo(4)
        assertThat(hand.holdsHiddenCards).isTrue
        assertThat(hand.openCards).containsExactlyInAnyOrder(CJ, SJ)

        hand.removeCard(HiddenCard)

        assertThat(hand.cards).containsExactlyInAnyOrder(CJ, SJ, HiddenCard)
        assertThat(hand.cardValues).isEqualTo(4)
        assertThat(hand.holdsHiddenCards).isTrue
        assertThat(hand.openCards).containsExactlyInAnyOrder(CJ, SJ)

        hand.removeCard(CJ)

        assertThat(hand.cards).containsExactlyInAnyOrder(SJ, HiddenCard)
        assertThat(hand.cardValues).isEqualTo(2)
        assertThat(hand.holdsHiddenCards).isTrue
        assertThat(hand.openCards).containsExactlyInAnyOrder(SJ)

        hand.removeCard(HiddenCard)

        assertThat(hand.cards).containsExactlyInAnyOrder(SJ)
        assertThat(hand.cardValues).isEqualTo(2)
        assertThat(hand.holdsHiddenCards).isFalse
        assertThat(hand.openCards).containsExactlyInAnyOrder(SJ)
    }
}