package net.b0n541.pmcts.game.skat

import org.slf4j.LoggerFactory

object Skat {
    private val LOG = LoggerFactory.getLogger(Skat::class.java)

    @JvmStatic
    fun playGame() {

        val cardDeck = listOf(CJ, SJ, HJ, DJ)

        //        final Map<PlayerPosition, SkatPlayer> players = Map.of(
//                PlayerPosition.FOREHAND, new SkatPlayer(),
//                PlayerPosition.MIDDLEHAND, new SkatPlayer(),
//                PlayerPosition.REARHAND, new SkatPlayer());
//
//        final List<Trick> tricks = new ArrayList<>();
//
//        dealCards(cardDeck, players);
//
//        final SkatGameState gameState = new SkatGameState(SkatGameType.CLUBS, PlayerPosition.FOREHAND);
//
//        final PlayerPosition trickForeHand = PlayerPosition.FOREHAND;
//        do {
//            final Card firstCard = players.get(trickForeHand).playCard();
//            gameState.addMove(new SkatGameMove(trickForeHand, firstCard));
//
//            final PlayerPosition trickMiddleHand = trickForeHand.getNextPlayer();
//            final Card secondCard = players.get(trickMiddleHand).playCard();
//            gameState.addMove(new SkatGameMove(trickMiddleHand, secondCard));
//
//            final PlayerPosition trickRearHand = trickMiddleHand.getNextPlayer();
//            final Card thirdCard = players.get(trickRearHand).playCard();
//            gameState.addMove(new SkatGameMove(trickRearHand, thirdCard));
//
//            LOG.info("Trick finished {}, cards {}, trick winner {}", trick.isFinished(), trick.getCards(), trick.getTrickWinner());
//
//            trickForeHand = trick.getTrickWinner();

//        } while (!gameState.isGameFinished());
    }

    private fun dealCards(cardDeck: List<Card>, players: Map<PlayerPosition, SkatPlayer>) {
        players[PlayerPosition.FOREHAND]!!.takeCards(cardDeck.subList(0, 3))
        players[PlayerPosition.MIDDLEHAND]!!.takeCards(cardDeck.subList(10, 13))
        players[PlayerPosition.REARHAND]!!.takeCards(cardDeck.subList(20, 23))

        // TODO deal skat cards
        players[PlayerPosition.FOREHAND]!!.takeCards(cardDeck.subList(3, 7))
        players[PlayerPosition.MIDDLEHAND]!!.takeCards(cardDeck.subList(13, 17))
        players[PlayerPosition.REARHAND]!!.takeCards(cardDeck.subList(23, 27))
        players[PlayerPosition.FOREHAND]!!.takeCards(cardDeck.subList(7, 10))
        players[PlayerPosition.MIDDLEHAND]!!.takeCards(cardDeck.subList(17, 20))
        players[PlayerPosition.REARHAND]!!.takeCards(cardDeck.subList(27, 30))
    }
}