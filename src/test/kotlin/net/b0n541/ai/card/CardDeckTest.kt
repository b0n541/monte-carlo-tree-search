package net.b0n541.ai.card

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CardDeckTest {
    @Test
    fun stringRepresentation() {
        assertThat("CA CK CQ CJ CT C9 C8 C7 SA SK SQ SJ ST S9 S8 S7 HA HK HQ HJ HT H9 H8 H7 DA DK DQ DJ DT D9 D8 D7")
            .isEqualTo(CardDeck().toString())

    }

    @Test
    fun prettyPrintForHand() {
        println(
            printHand(
                Hand(
                    listOf(
                        HiddenCard(),
                        OpenCard(Suit.CLUBS, Rank.ACE),
                        OpenCard(Suit.SPADES, Rank.KING),
                        OpenCard(Suit.HEARTS, Rank.QUEEN),
                        OpenCard(Suit.DIAMONDS, Rank.JACK),
                        OpenCard(Suit.CLUBS, Rank.TEN),
                        OpenCard(Suit.SPADES, Rank.NINE),
                        OpenCard(Suit.HEARTS, Rank.EIGHT),
                        OpenCard(Suit.DIAMONDS, Rank.SEVEN),
                        OpenCard(Suit.CLUBS, Rank.SIX),
                        OpenCard(Suit.SPADES, Rank.FIVE),
                        OpenCard(Suit.HEARTS, Rank.FOUR),
                        OpenCard(Suit.DIAMONDS, Rank.THREE),
                        OpenCard(Suit.CLUBS, Rank.TWO),
                    )
                )
            )
        )
    }
}