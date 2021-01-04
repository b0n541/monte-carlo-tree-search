package net.b0n541.pmcts.game.skat

import net.b0n541.pmcts.game.skat.Rank.Companion.rankOf
import net.b0n541.pmcts.game.skat.Suit.Companion.suitOf
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class CardTest {
    @Test
    fun suits() {
        assertThat(Suit.CLUBS.symbol).isEqualTo("C")
        assertThat(Suit.SPADES.symbol).isEqualTo("S")
        assertThat(Suit.HEARTS.symbol).isEqualTo("H")
        assertThat(Suit.DIAMONDS.symbol).isEqualTo("D")

        assertThat(Suit.CLUBS.toString()).isEqualTo("♣")
        assertThat(Suit.SPADES.toString()).isEqualTo("♠")
        assertThat(Suit.HEARTS.toString()).isEqualTo("♥")
        assertThat(Suit.DIAMONDS.toString()).isEqualTo("♦")

        assertThat(Suit.CLUBS.rank > Suit.SPADES.rank).isTrue
        assertThat(Suit.SPADES.rank > Suit.HEARTS.rank).isTrue
        assertThat(Suit.HEARTS.rank > Suit.DIAMONDS.rank).isTrue

        assertThat(suitOf("C")).isEqualTo(Suit.CLUBS)
        assertThat(suitOf("S")).isEqualTo(Suit.SPADES)
        assertThat(suitOf("H")).isEqualTo(Suit.HEARTS)
        assertThat(suitOf("D")).isEqualTo(Suit.DIAMONDS)

        assertThrows<IllegalArgumentException> { suitOf("X") }
    }

    @Test
    fun ranks() {
        assertThat(Rank.SEVEN.symbol).isEqualTo("7")
        assertThat(Rank.EIGHT.symbol).isEqualTo("8")
        assertThat(Rank.NINE.symbol).isEqualTo("9")
        assertThat(Rank.TEN.symbol).isEqualTo("T")
        assertThat(Rank.JACK.symbol).isEqualTo("J")
        assertThat(Rank.QUEEN.symbol).isEqualTo("Q")
        assertThat(Rank.KING.symbol).isEqualTo("K")
        assertThat(Rank.ACE.symbol).isEqualTo("A")

        assertThat(Rank.SEVEN.toString()).isEqualTo("7")
        assertThat(Rank.EIGHT.toString()).isEqualTo("8")
        assertThat(Rank.NINE.toString()).isEqualTo("9")
        assertThat(Rank.TEN.toString()).isEqualTo("T")
        assertThat(Rank.JACK.toString()).isEqualTo("J")
        assertThat(Rank.QUEEN.toString()).isEqualTo("Q")
        assertThat(Rank.KING.toString()).isEqualTo("K")
        assertThat(Rank.ACE.toString()).isEqualTo("A")

        assertThat(Rank.SEVEN.value).isEqualTo(0)
        assertThat(Rank.EIGHT.value).isEqualTo(0)
        assertThat(Rank.NINE.value).isEqualTo(0)
        assertThat(Rank.TEN.value).isEqualTo(10)
        assertThat(Rank.JACK.value).isEqualTo(2)
        assertThat(Rank.QUEEN.value).isEqualTo(3)
        assertThat(Rank.KING.value).isEqualTo(4)
        assertThat(Rank.ACE.value).isEqualTo(11)

        assertThat(Rank.JACK.suitGameRank > Rank.ACE.suitGameRank).isTrue
        assertThat(Rank.ACE.suitGameRank > Rank.TEN.suitGameRank).isTrue
        assertThat(Rank.TEN.suitGameRank > Rank.KING.suitGameRank).isTrue
        assertThat(Rank.KING.suitGameRank > Rank.QUEEN.suitGameRank).isTrue
        assertThat(Rank.QUEEN.suitGameRank > Rank.NINE.suitGameRank).isTrue
        assertThat(Rank.NINE.suitGameRank > Rank.EIGHT.suitGameRank).isTrue
        assertThat(Rank.EIGHT.suitGameRank > Rank.SEVEN.suitGameRank).isTrue

        assertThat(Rank.ACE.nullGameRank > Rank.KING.nullGameRank).isTrue
        assertThat(Rank.KING.nullGameRank > Rank.QUEEN.nullGameRank).isTrue
        assertThat(Rank.QUEEN.nullGameRank > Rank.JACK.nullGameRank).isTrue
        assertThat(Rank.JACK.nullGameRank > Rank.TEN.nullGameRank).isTrue
        assertThat(Rank.TEN.nullGameRank > Rank.NINE.nullGameRank).isTrue
        assertThat(Rank.NINE.nullGameRank > Rank.EIGHT.nullGameRank).isTrue
        assertThat(Rank.EIGHT.nullGameRank > Rank.SEVEN.nullGameRank).isTrue

        assertThat(rankOf("A")).isEqualTo(Rank.ACE)
        assertThat(rankOf("K")).isEqualTo(Rank.KING)
        assertThat(rankOf("Q")).isEqualTo(Rank.QUEEN)
        assertThat(rankOf("J")).isEqualTo(Rank.JACK)
        assertThat(rankOf("T")).isEqualTo(Rank.TEN)
        assertThat(rankOf("9")).isEqualTo(Rank.NINE)
        assertThat(rankOf("8")).isEqualTo(Rank.EIGHT)
        assertThat(rankOf("7")).isEqualTo(Rank.SEVEN)

        assertThrows<IllegalArgumentException> { rankOf("X") }
    }

    @Test
    fun hiddenCard() {
        assertThat(HiddenCard.toString()).isEqualTo("??")
    }

    @Test
    fun openCard() {
        assertThat(card("CJ")).isEqualTo(OpenCard(Suit.CLUBS, Rank.JACK))
        assertThrows<IllegalArgumentException> { card("XJ") }
        assertThrows<IllegalArgumentException> { card("CX") }
    }

    @Test
    fun isTrump() {
        checkIsTrump(setOf(CJ, SJ, HJ, DJ), SkatGameType.GRAND)
        checkIsNotTrump(setOf(CA, CT, CK, CQ, C9, C8, C7), SkatGameType.GRAND)
        checkIsNotTrump(setOf(SA, ST, SK, SQ, S9, S8, S7), SkatGameType.GRAND)
        checkIsNotTrump(setOf(HA, HT, HK, HQ, H9, H8, H7), SkatGameType.GRAND)
        checkIsNotTrump(setOf(DA, DT, DK, DQ, D9, D8, D7), SkatGameType.GRAND)

        checkIsTrump(setOf(CJ, SJ, HJ, DJ, CA, CT, CK, CQ, C9, C8, C7), SkatGameType.CLUBS)
        checkIsNotTrump(setOf(SA, ST, SK, SQ, S9, S8, S7), SkatGameType.CLUBS)
        checkIsNotTrump(setOf(HA, HT, HK, HQ, H9, H8, H7), SkatGameType.CLUBS)
        checkIsNotTrump(setOf(DA, DT, DK, DQ, D9, D8, D7), SkatGameType.CLUBS)

        checkIsNotTrump(setOf(CA, CT, CK, CQ, C9, C8, C7), SkatGameType.SPADES)
        checkIsTrump(setOf(CJ, SJ, HJ, DJ, SA, ST, SK, SQ, S9, S8, S7), SkatGameType.SPADES)
        checkIsNotTrump(setOf(HA, HT, HK, HQ, H9, H8, H7), SkatGameType.SPADES)
        checkIsNotTrump(setOf(DA, DT, DK, DQ, D9, D8, D7), SkatGameType.SPADES)

        checkIsNotTrump(setOf(CA, CT, CK, CQ, C9, C8, C7), SkatGameType.HEARTS)
        checkIsNotTrump(setOf(SA, ST, SK, SQ, S9, S8, S7), SkatGameType.HEARTS)
        checkIsTrump(setOf(CJ, SJ, HJ, DJ, HA, HT, HK, HQ, H9, H8, H7), SkatGameType.HEARTS)
        checkIsNotTrump(setOf(DA, DT, DK, DQ, D9, D8, D7), SkatGameType.HEARTS)

        checkIsNotTrump(setOf(CA, CT, CK, CQ, C9, C8, C7), SkatGameType.DIAMONDS)
        checkIsNotTrump(setOf(SA, ST, SK, SQ, S9, S8, S7), SkatGameType.DIAMONDS)
        checkIsNotTrump(setOf(HA, HT, HK, HQ, H9, H8, H7), SkatGameType.DIAMONDS)
        checkIsTrump(setOf(CJ, SJ, HJ, DJ, DA, DT, DK, DQ, D9, D8, D7), SkatGameType.DIAMONDS)

        checkIsNotTrump(PERFECT_CARD_DISTRIBUTION.sortedCards.toSet(), SkatGameType.NULL)
    }

    private fun checkIsTrump(cards: Set<OpenCard>, gameType: SkatGameType) {
        cards.forEach { assertThat(it.isTrump(gameType)).isTrue }
    }

    private fun checkIsNotTrump(cards: Set<OpenCard>, gameType: SkatGameType) {
        cards.forEach { assertThat(it.isTrump(gameType)).isFalse }
    }

    @Test
    fun isAllowedOn() {
        assertThat(SJ.isAllowedOn(CJ, SkatGameType.CLUBS, Hand())).isTrue
    }
}