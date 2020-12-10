package org.b0n541.pmcts.mcts;

public enum TestGameMove implements GameMove {
    HEADS,
    TAILS;

    @Override
    public String toShortString() {
        return name();
    }
}
