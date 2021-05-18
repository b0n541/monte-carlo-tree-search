package net.b0n541.ai.game.skat

import org.slf4j.LoggerFactory

object Skat {

    private val LOG = LoggerFactory.getLogger(javaClass)

    @JvmStatic
    fun playGame() {
        val players = mapOf(
            PlayerPosition.FOREHAND to SkatPlayer(),
            PlayerPosition.MIDDLEHAND to SkatPlayer(),
            PlayerPosition.REARHAND to SkatPlayer()
        )

        var gameState = SkatGameState(SkatGameType.CLUBS, PlayerPosition.FOREHAND, PlayerPosition.FOREHAND)

        val cardDeck = CardDeck()
        cardDeck.shuffle()

        dealCards(cardDeck, players, gameState)

        var trickForeHand = gameState.nextPlayerPosition
        do {
            val firstCard = players[trickForeHand]!!.play()
            gameState = gameState.addMove(firstCard)

            val trickMiddleHand = trickForeHand.nextPlayer
            val secondCard = players[trickMiddleHand]!!.play()
            gameState = gameState.addMove(secondCard)

            val trickRearHand = trickMiddleHand.nextPlayer
            val thirdCard = players[trickRearHand]!!.play()
            gameState = gameState.addMove(thirdCard)

            trickForeHand = gameState.nextPlayerPosition

        } while (!gameState.isGameFinished)

        LOG.info("Game finished: ${gameState.gameValues}")
    }

    private fun dealCards(
        cardDeck: CardDeck,
        players: Map<PlayerPosition, SkatPlayer>,
        gameState: SkatGameState
    ) {
        val foreHandCards = cardDeck.dealCards(PlayerPosition.FOREHAND)
        players[PlayerPosition.FOREHAND]?.takeCards(foreHandCards)
        gameState.dealPlayerCards(PlayerPosition.FOREHAND, foreHandCards.toList())

        val middleHandCards = cardDeck.dealCards(PlayerPosition.MIDDLEHAND)
        players[PlayerPosition.MIDDLEHAND]?.takeCards(middleHandCards)
        gameState.dealPlayerCards(PlayerPosition.MIDDLEHAND, middleHandCards.toList())

        val rearHandCards = cardDeck.dealCards(PlayerPosition.REARHAND)
        players[PlayerPosition.REARHAND]?.takeCards(rearHandCards)
        gameState.dealPlayerCards(PlayerPosition.REARHAND, rearHandCards.toList())

        gameState.dealSkat(cardDeck.dealSkat().toMutableList())
    }
}