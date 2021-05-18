package net.b0n541.ai.game.common.card

sealed class Card

class HiddenCard : Card() {
    override fun toString(): String {
        return "XX"
    }
}

class OpenCard(val suit: Suit, val rank: Rank) : Card() {
    override fun toString(): String {
        return suit.toString() + rank.toString()
    }
}

enum class Suit(val symbol: String) {
    CLUBS("♠"),
    SPADES("♣"),
    HEARTS("♥"),
    DIAMONDS("♦");

    override fun toString(): String {
        return name.first().toString()
    }
}

enum class Rank(val shortString: String) {
    ACE("A"),
    KING("K"),
    QUEEN("Q"),
    JACK("J"),
    TEN("T"),
    NINE("9"),
    EIGHT("8"),
    SEVEN("7"),
    SIX("6"),
    FIVE("5"),
    FOUR("4"),
    THREE("3"),
    TWO("2");

    override fun toString(): String {
        return shortString
    }
}