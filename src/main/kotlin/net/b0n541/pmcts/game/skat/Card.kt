package net.b0n541.pmcts.game.skat

import net.b0n541.pmcts.game.skat.Rank.Companion.rankOf
import net.b0n541.pmcts.game.skat.Suit.Companion.suitOf

sealed class Card
data class OpenCard(val suit: Suit, val rank: Rank) : Card() {
    // TODO use delegate by Rank.value?
    val value = rank.value

    fun isAllowedOn(leadingCard: OpenCard, gameType: SkatGameType, hand: Hand): Boolean {
        return when (gameType) {
            SkatGameType.GRAND -> (leadingCard.isTrump(gameType) and isTrump(gameType)) or !leadingCard.isTrump(gameType)
            SkatGameType.CLUBS -> isFollowingSuit(leadingCard) or (leadingCard.isTrump(gameType) and isTrump(
                gameType
            ))
            else -> false
        }
    }

    fun isFollowingSuit(leadingCard: OpenCard) = leadingCard.suit == suit

    fun isTrump(gameType: SkatGameType): Boolean {
        return when (gameType) {
            SkatGameType.GRAND -> isJack()
            SkatGameType.CLUBS -> isJack() or (suit == Suit.CLUBS)
            SkatGameType.SPADES -> isJack() or (suit == Suit.SPADES)
            SkatGameType.HEARTS -> isJack() or (suit == Suit.HEARTS)
            SkatGameType.DIAMONDS -> isJack() or (suit == Suit.DIAMONDS)
            SkatGameType.NULL -> false
        }
    }

    private fun isJack() = JACKS.contains(this)

    override fun toString() = suit.toString() + rank.toString()
}

object HiddenCard : Card() {
    override fun toString() = "??"
}

fun card(string: String): OpenCard =
    OpenCard(suitOf(string.substring(0..0)), rankOf(string.substring(1..1)))

enum class Suit(val symbol: String, private val prettySymbol: String, val rank: Int) {
    CLUBS("C", "♣", 4),
    SPADES("S", "♠", 3),
    HEARTS("H", "♥", 2),
    DIAMONDS("D", "♦", 1);

    override fun toString() = prettySymbol

    companion object {
        fun suitOf(symbol: String): Suit =
            enumValues<Suit>().find { it.symbol == symbol } ?: throw IllegalArgumentException("Unknown suit $symbol")
    }
}

enum class Rank(val symbol: String, val value: Int, val suitGameRank: Int, val nullGameRank: Int) {
    SEVEN("7", 0, 1, 1),
    EIGHT("8", 0, 2, 2),
    NINE("9", 0, 3, 3),
    TEN("T", 10, 6, 4),
    JACK("J", 2, 8, 5),
    QUEEN("Q", 3, 4, 6),
    KING("K", 4, 5, 7),
    ACE("A", 11, 7, 8);

    override fun toString() = symbol

    companion object {
        fun rankOf(symbol: String): Rank =
            enumValues<Rank>().find { it.symbol == symbol } ?: throw IllegalArgumentException("Unknown rank $symbol")
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

val JACKS = setOf(CJ, SJ, HJ, DJ)
