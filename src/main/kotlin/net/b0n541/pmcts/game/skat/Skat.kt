package net.b0n541.pmcts.game.skat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static net.b0n541.pmcts.game.skat.Card.*;

public class Skat {

    private static final Logger LOG = LoggerFactory.getLogger(Skat.class);

    public static void playGame() {

        final List<Card> cardDeck = List.of(
                CJ, SJ, HJ, DJ, CA, CT, CK, CQ, C9, C8,
                C7, SA, ST, SK, SQ, S9, S8, S7, HA, HT,
                HK, HQ, H9, H8, H7, DA, DT, DK, DQ, D9,
                D8, D7);

        final Map<PlayerPosition, SkatPlayer> players = Map.of(
                PlayerPosition.FOREHAND, new SkatPlayer(),
                PlayerPosition.MIDDLEHAND, new SkatPlayer(),
                PlayerPosition.REARHAND, new SkatPlayer());

        final List<Trick> tricks = new ArrayList<>();

        dealCards(cardDeck, players);

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

    private static void dealCards(final List<Card> cardDeck, final Map<PlayerPosition, SkatPlayer> players) {
        players.get(PlayerPosition.FOREHAND).takeCards(cardDeck.subList(0, 3));
        players.get(PlayerPosition.MIDDLEHAND).takeCards(cardDeck.subList(10, 13));
        players.get(PlayerPosition.REARHAND).takeCards(cardDeck.subList(20, 23));

        // TODO deal skat cards

        players.get(PlayerPosition.FOREHAND).takeCards(cardDeck.subList(3, 7));
        players.get(PlayerPosition.MIDDLEHAND).takeCards(cardDeck.subList(13, 17));
        players.get(PlayerPosition.REARHAND).takeCards(cardDeck.subList(23, 27));

        players.get(PlayerPosition.FOREHAND).takeCards(cardDeck.subList(7, 10));
        players.get(PlayerPosition.MIDDLEHAND).takeCards(cardDeck.subList(17, 20));
        players.get(PlayerPosition.REARHAND).takeCards(cardDeck.subList(27, 30));
    }
}
