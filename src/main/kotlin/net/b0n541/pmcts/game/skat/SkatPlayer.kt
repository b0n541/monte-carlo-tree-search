package net.b0n541.pmcts.game.skat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SkatPlayer {

    private final List<Card> hand = new ArrayList<>();

    public void takeCards(final List<Card> cards) {
        hand.addAll(cards);
    }

    public Card playCard() {
        return hand.remove(ThreadLocalRandom.current().nextInt(hand.size()));
    }
}
