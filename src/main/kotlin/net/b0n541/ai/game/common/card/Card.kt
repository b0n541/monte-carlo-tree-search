package net.b0n541.ai.game.common.card

sealed class Card

object HiddenCard : Card() {
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

enum class Rank(private val shortString: String) {
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

val CA = OpenCard(Suit.CLUBS, Rank.ACE)
val CK = OpenCard(Suit.CLUBS, Rank.KING)
val CQ = OpenCard(Suit.CLUBS, Rank.QUEEN)
val CJ = OpenCard(Suit.CLUBS, Rank.JACK)
val CT = OpenCard(Suit.CLUBS, Rank.TEN)
val C9 = OpenCard(Suit.CLUBS, Rank.NINE)
val C8 = OpenCard(Suit.CLUBS, Rank.EIGHT)
val C7 = OpenCard(Suit.CLUBS, Rank.SEVEN)
val SA = OpenCard(Suit.SPADES, Rank.ACE)
val SK = OpenCard(Suit.SPADES, Rank.KING)
val SQ = OpenCard(Suit.SPADES, Rank.QUEEN)
val SJ = OpenCard(Suit.SPADES, Rank.JACK)
val ST = OpenCard(Suit.SPADES, Rank.TEN)
val S9 = OpenCard(Suit.SPADES, Rank.NINE)
val S8 = OpenCard(Suit.SPADES, Rank.EIGHT)
val S7 = OpenCard(Suit.SPADES, Rank.SEVEN)
val HA = OpenCard(Suit.HEARTS, Rank.ACE)
val HK = OpenCard(Suit.HEARTS, Rank.KING)
val HQ = OpenCard(Suit.HEARTS, Rank.QUEEN)
val HJ = OpenCard(Suit.HEARTS, Rank.JACK)
val HT = OpenCard(Suit.HEARTS, Rank.TEN)
val H9 = OpenCard(Suit.HEARTS, Rank.NINE)
val H8 = OpenCard(Suit.HEARTS, Rank.EIGHT)
val H7 = OpenCard(Suit.HEARTS, Rank.SEVEN)
val DA = OpenCard(Suit.DIAMONDS, Rank.ACE)
val DK = OpenCard(Suit.DIAMONDS, Rank.KING)
val DQ = OpenCard(Suit.DIAMONDS, Rank.QUEEN)
val DJ = OpenCard(Suit.DIAMONDS, Rank.JACK)
val DT = OpenCard(Suit.DIAMONDS, Rank.TEN)
val D9 = OpenCard(Suit.DIAMONDS, Rank.NINE)
val D8 = OpenCard(Suit.DIAMONDS, Rank.EIGHT)
val D7 = OpenCard(Suit.DIAMONDS, Rank.SEVEN)
