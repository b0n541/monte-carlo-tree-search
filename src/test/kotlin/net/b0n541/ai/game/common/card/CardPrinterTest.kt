package net.b0n541.ai.game.common.card

import org.junit.jupiter.api.Test

internal class CardPrinterTest {
    @Test
    fun printSchwimmen() {
        println(
            printHands(
                listOf(
                    Hand(listOf(HiddenCard, HiddenCard, HiddenCard)),
                    Hand(listOf(HiddenCard, HiddenCard, HiddenCard))
                ),
                blanks = 8
            )
        )
        println(printHand(Hand(listOf(DJ, HT, SA)), leftIndent = 16))
        println(printHand(Hand(listOf(D7, H7, S7)), leftIndent = 16))
    }
}