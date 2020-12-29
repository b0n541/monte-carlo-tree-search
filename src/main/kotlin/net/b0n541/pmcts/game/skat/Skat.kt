package net.b0n541.pmcts.game.skat

import org.slf4j.LoggerFactory

object Skat {

    private val LOG = LoggerFactory.getLogger(javaClass)

    @JvmStatic
    fun playGame() {

        val cardDeck = CardDeck().shuffle()

        val players = mapOf(
            PlayerPosition.FOREHAND to SkatPlayer(),
            PlayerPosition.MIDDLEHAND to SkatPlayer(),
            PlayerPosition.REARHAND to SkatPlayer()
        )

        var gameState = SkatGameState(SkatGameType.CLUBS, PlayerPosition.FOREHAND, PlayerPosition.FOREHAND)

        dealCards(cardDeck, players)

        var trickForeHand = gameState.nextPlayerPosition
        do {
            val firstCard = players[trickForeHand]!!.playCard()
            gameState = gameState.addMove(SkatGameMove(trickForeHand, firstCard))

            val trickMiddleHand = trickForeHand.nextPlayer
            val secondCard = players[trickMiddleHand]!!.playCard()
            gameState = gameState.addMove(SkatGameMove(trickMiddleHand, secondCard))

            val trickRearHand = trickMiddleHand.nextPlayer
            val thirdCard = players[trickRearHand]!!.playCard()
            gameState = gameState.addMove(SkatGameMove(trickRearHand, thirdCard))

            trickForeHand = gameState.nextPlayerPosition

        } while (!gameState.isGameFinished)
    }

    private fun dealCards(cardDeck: List<OpenCard>, players: Map<PlayerPosition, SkatPlayer>) {

        players[PlayerPosition.FOREHAND]?.takeCards(cardDeck.subList(0, 3))
        players[PlayerPosition.MIDDLEHAND]?.takeCards(cardDeck.subList(10, 13))
        players[PlayerPosition.REARHAND]?.takeCards(cardDeck.subList(20, 23))

        // TODO deal skat cards

        players[PlayerPosition.FOREHAND]?.takeCards(cardDeck.subList(3, 7))
        players[PlayerPosition.MIDDLEHAND]?.takeCards(cardDeck.subList(13, 17))
        players[PlayerPosition.REARHAND]?.takeCards(cardDeck.subList(23, 27))

        players[PlayerPosition.FOREHAND]?.takeCards(cardDeck.subList(7, 10))
        players[PlayerPosition.MIDDLEHAND]?.takeCards(cardDeck.subList(17, 20))
        players[PlayerPosition.REARHAND]?.takeCards(cardDeck.subList(27, 30))
    }
}