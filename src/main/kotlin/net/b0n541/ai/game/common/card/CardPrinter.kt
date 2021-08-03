package net.b0n541.ai.game.common.card

const val TOP_OF_CARD_LINE = " _____ "

const val HIDDEN_CARD_LINE = "|\u001B[34m#####\u001B[0m|"

const val EMPTY_LINE = "|     |"
const val ONE_SYMBOL_LINE = "|  *  |"
const val TWO_SYMBOLS_LINE = "| * * |"
const val THREE_SYMBOLS_LINE = "|* * *|"

const val ACE_TOP_LINE = "|A*   |"
const val ACE_BOTTOM_LINE = "|___*A|"

const val KING_TOP_LINE = "|K* WW|"
const val KING_BOTTOM_LINE = "|_***K|"

const val QUEEN_TOP_LINE = "|Q* ww|"
const val QUEEN_BOTTOM_LINE = "|_***Q|"

const val JACK_TOP_LINE = "|J*  w|"
const val JACK_BOTTOM_LINE = "|__**J|"

const val MALE_HEAD_LINE = "|   {)|"
const val FEMALE_HEAD_LINE = "|   {(|"

const val TWO_SYMBOLS_BODY_LINE = "|   **|"
const val THREE_SYMBOLS_BODY_LINE = "|  ***|"

const val TEN_TOP_LINE = "|10* *|"
const val TEN_BOTTOM_LINE = "|__*10|"

const val SINGLE_NUMBER_TOP_LINE = "|#*   |"
const val SINGLE_NUMBER_BOTTOM_LINE = "|___*#|"

val ACE_CARD = listOf(
    TOP_OF_CARD_LINE,
    ACE_TOP_LINE,
    EMPTY_LINE,
    ONE_SYMBOL_LINE,
    EMPTY_LINE,
    ACE_BOTTOM_LINE
)

val KING_CARD = listOf(
    TOP_OF_CARD_LINE,
    KING_TOP_LINE,
    MALE_HEAD_LINE,
    TWO_SYMBOLS_BODY_LINE,
    THREE_SYMBOLS_BODY_LINE,
    KING_BOTTOM_LINE
)

val QUEEN_CARD = listOf(
    TOP_OF_CARD_LINE,
    QUEEN_TOP_LINE,
    FEMALE_HEAD_LINE,
    TWO_SYMBOLS_BODY_LINE,
    THREE_SYMBOLS_BODY_LINE,
    QUEEN_BOTTOM_LINE
)

val JACK_CARD = listOf(
    TOP_OF_CARD_LINE,
    JACK_TOP_LINE,
    MALE_HEAD_LINE,
    TWO_SYMBOLS_BODY_LINE,
    TWO_SYMBOLS_BODY_LINE,
    JACK_BOTTOM_LINE
)

val TEN_CARD = listOf(
    TOP_OF_CARD_LINE,
    TEN_TOP_LINE,
    THREE_SYMBOLS_LINE,
    THREE_SYMBOLS_LINE,
    THREE_SYMBOLS_LINE,
    TEN_BOTTOM_LINE
)

val NINE_CARD = listOf(
    TOP_OF_CARD_LINE,
    SINGLE_NUMBER_TOP_LINE.replace("#", "9"),
    THREE_SYMBOLS_LINE,
    THREE_SYMBOLS_LINE,
    THREE_SYMBOLS_LINE,
    SINGLE_NUMBER_BOTTOM_LINE.replace("#", "9"),
)

val EIGHT_CARD = listOf(
    TOP_OF_CARD_LINE,
    SINGLE_NUMBER_TOP_LINE.replace("#", "8"),
    THREE_SYMBOLS_LINE,
    TWO_SYMBOLS_LINE,
    THREE_SYMBOLS_LINE,
    SINGLE_NUMBER_BOTTOM_LINE.replace("#", "8"),
)

val SEVEN_CARD = listOf(
    TOP_OF_CARD_LINE,
    SINGLE_NUMBER_TOP_LINE.replace("#", "7"),
    TWO_SYMBOLS_LINE,
    THREE_SYMBOLS_LINE,
    TWO_SYMBOLS_LINE,
    SINGLE_NUMBER_BOTTOM_LINE.replace("#", "7"),
)

val SIX_CARD = listOf(
    TOP_OF_CARD_LINE,
    SINGLE_NUMBER_TOP_LINE.replace("#", "6"),
    TWO_SYMBOLS_LINE,
    TWO_SYMBOLS_LINE,
    TWO_SYMBOLS_LINE,
    SINGLE_NUMBER_BOTTOM_LINE.replace("#", "6"),
)

val FIVE_CARD = listOf(
    TOP_OF_CARD_LINE,
    SINGLE_NUMBER_TOP_LINE.replace("#", "5"),
    TWO_SYMBOLS_LINE,
    ONE_SYMBOL_LINE,
    TWO_SYMBOLS_LINE,
    SINGLE_NUMBER_BOTTOM_LINE.replace("#", "5"),
)

val FOUR_CARD = listOf(
    TOP_OF_CARD_LINE,
    SINGLE_NUMBER_TOP_LINE.replace("#", "4"),
    TWO_SYMBOLS_LINE,
    EMPTY_LINE,
    TWO_SYMBOLS_LINE,
    SINGLE_NUMBER_BOTTOM_LINE.replace("#", "4"),
)

val THREE_CARD = listOf(
    TOP_OF_CARD_LINE,
    SINGLE_NUMBER_TOP_LINE.replace("#", "3"),
    TWO_SYMBOLS_LINE,
    EMPTY_LINE,
    ONE_SYMBOL_LINE,
    SINGLE_NUMBER_BOTTOM_LINE.replace("#", "3"),
)

val TWO_CARD = listOf(
    TOP_OF_CARD_LINE,
    SINGLE_NUMBER_TOP_LINE.replace("#", "2"),
    ONE_SYMBOL_LINE,
    EMPTY_LINE,
    ONE_SYMBOL_LINE,
    SINGLE_NUMBER_BOTTOM_LINE.replace("#", "2"),
)

val JOKER_CARD = listOf(
    TOP_OF_CARD_LINE,
    "|J  * |",
    "| O  *|",
    "|  K  |",
    "|*  E |",
    "|_*__R|"
)

val CARDS = mapOf(
    Rank.ACE to ACE_CARD,
    Rank.KING to KING_CARD,
    Rank.QUEEN to QUEEN_CARD,
    Rank.JACK to JACK_CARD,
    Rank.TEN to TEN_CARD,
    Rank.NINE to NINE_CARD,
    Rank.EIGHT to EIGHT_CARD,
    Rank.SEVEN to SEVEN_CARD,
    Rank.SIX to SIX_CARD,
    Rank.FIVE to FIVE_CARD,
    Rank.FOUR to FOUR_CARD,
    Rank.THREE to THREE_CARD,
    Rank.TWO to TWO_CARD,
)

fun printHand(hand: Hand): String {

    var result = ""

    for (line in 0 until 6) {
        for (card in hand.cards) {
            var cardString = ""
            cardString += when (card) {
                is HiddenCard -> if (line == 0) TOP_OF_CARD_LINE else HIDDEN_CARD_LINE
                is OpenCard -> getOpenCardLine(line, card).replace("*", getSuitSymbolInAnsiColor(card.suit))
            }
            result += "$cardString "
        }
        result += "\n"
    }

    return result
}

private fun getSuitSymbolInAnsiColor(suit: Suit): String {
    var result = ""
    when (suit) {
        Suit.SPADES -> result += "\u001B[32m"
        Suit.HEARTS -> result += "\u001B[31m"
        Suit.DIAMONDS -> result += "\u001B[33m"
    }
    result += suit.symbol + "\u001B[0m"

    return result
}

fun getOpenCardLine(line: Int, card: OpenCard): String {
    return CARDS[card.rank]?.get(line) ?: ""
}
