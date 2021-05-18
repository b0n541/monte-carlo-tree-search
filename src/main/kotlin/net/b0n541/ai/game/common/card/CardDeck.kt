package net.b0n541.ai.game.common.card

class CardDeck(
    val cards: List<OpenCard> = listOf(
        OpenCard(Suit.CLUBS, Rank.ACE),
        OpenCard(Suit.CLUBS, Rank.KING),
        OpenCard(Suit.CLUBS, Rank.QUEEN),
        OpenCard(Suit.CLUBS, Rank.JACK),
        OpenCard(Suit.CLUBS, Rank.TEN),
        OpenCard(Suit.CLUBS, Rank.NINE),
        OpenCard(Suit.CLUBS, Rank.EIGHT),
        OpenCard(Suit.CLUBS, Rank.SEVEN),
        OpenCard(Suit.SPADES, Rank.ACE),
        OpenCard(Suit.SPADES, Rank.KING),
        OpenCard(Suit.SPADES, Rank.QUEEN),
        OpenCard(Suit.SPADES, Rank.JACK),
        OpenCard(Suit.SPADES, Rank.TEN),
        OpenCard(Suit.SPADES, Rank.NINE),
        OpenCard(Suit.SPADES, Rank.EIGHT),
        OpenCard(Suit.SPADES, Rank.SEVEN),
        OpenCard(Suit.HEARTS, Rank.ACE),
        OpenCard(Suit.HEARTS, Rank.KING),
        OpenCard(Suit.HEARTS, Rank.QUEEN),
        OpenCard(Suit.HEARTS, Rank.JACK),
        OpenCard(Suit.HEARTS, Rank.TEN),
        OpenCard(Suit.HEARTS, Rank.NINE),
        OpenCard(Suit.HEARTS, Rank.EIGHT),
        OpenCard(Suit.HEARTS, Rank.SEVEN),
        OpenCard(Suit.DIAMONDS, Rank.ACE),
        OpenCard(Suit.DIAMONDS, Rank.KING),
        OpenCard(Suit.DIAMONDS, Rank.QUEEN),
        OpenCard(Suit.DIAMONDS, Rank.JACK),
        OpenCard(Suit.DIAMONDS, Rank.TEN),
        OpenCard(Suit.DIAMONDS, Rank.NINE),
        OpenCard(Suit.DIAMONDS, Rank.EIGHT),
        OpenCard(Suit.DIAMONDS, Rank.SEVEN)
    )
) {
    override fun toString(): String {
        return cards.joinToString(separator = " ")
    }
}