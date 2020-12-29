package net.b0n541.pmcts.game.skat;

public enum PlayerPosition {
    FOREHAND,
    MIDDLEHAND,
    REARHAND;

    public PlayerPosition getNextPlayer() {
        final PlayerPosition result;
        switch (this) {
            case FOREHAND:
                result = MIDDLEHAND;
                break;
            case MIDDLEHAND:
                result = REARHAND;
                break;
            case REARHAND:
                result = FOREHAND;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
        return result;
    }
}
