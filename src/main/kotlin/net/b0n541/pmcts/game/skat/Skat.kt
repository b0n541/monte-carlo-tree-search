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

        dealCards(cardDeck, players, gameState)

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

        LOG.info("Game finished: ${gameState.gameValues}")
    }

    private fun dealCards(
        cardDeck: List<OpenCard>,
        players: Map<PlayerPosition, SkatPlayer>,
        gameState: SkatGameState
    ) {
        val foreHandCards = cardDeck.subList(0, 10)
        players[PlayerPosition.FOREHAND]?.takeCards(foreHandCards)
        gameState.dealPlayerCards(PlayerPosition.FOREHAND, foreHandCards)

        val middleHandCards = cardDeck.subList(10, 20)
        players[PlayerPosition.MIDDLEHAND]?.takeCards(middleHandCards)
        gameState.dealPlayerCards(PlayerPosition.MIDDLEHAND, middleHandCards)

        val rearHandCards = cardDeck.subList(20, 30)
        players[PlayerPosition.REARHAND]?.takeCards(rearHandCards)
        gameState.dealPlayerCards(PlayerPosition.REARHAND, rearHandCards)

        gameState.dealSkat(cardDeck.subList(30, 32))
    }
}