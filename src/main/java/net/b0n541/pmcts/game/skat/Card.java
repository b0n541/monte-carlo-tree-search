package net.b0n541.pmcts.game.skat;

public enum Card {
    /**
     * Ace of clubs
     */
    CA(Suit.CLUBS, Rank.ACE),
    /**
     * Ten of clubs
     */
    CT(Suit.CLUBS, Rank.TEN),
    /**
     * King of clubs
     */
    CK(Suit.CLUBS, Rank.KING),
    /**
     * Queen of clubs
     */
    CQ(Suit.CLUBS, Rank.QUEEN),
    /**
     * Jack of clubs
     */
    CJ(Suit.CLUBS, Rank.JACK),
    /**
     * Nine of clubs
     */
    C9(Suit.CLUBS, Rank.NINE),
    /**
     * Eight of clubs
     */
    C8(Suit.CLUBS, Rank.EIGHT),
    /**
     * Seven of clubs
     */
    C7(Suit.CLUBS, Rank.SEVEN),
    /**
     * Ace of spades
     */
    SA(Suit.SPADES, Rank.ACE),
    /**
     * Ten of spades
     */
    ST(Suit.SPADES, Rank.TEN),
    /**
     * King of spades
     */
    SK(Suit.SPADES, Rank.KING),
    /**
     * Queen of spades
     */
    SQ(Suit.SPADES, Rank.QUEEN),
    /**
     * Jack of spades
     */
    SJ(Suit.SPADES, Rank.JACK),
    /**
     * Nine of spades
     */
    S9(Suit.SPADES, Rank.NINE),
    /**
     * Eight of spades
     */
    S8(Suit.SPADES, Rank.EIGHT),
    /**
     * Seven of spades
     */
    S7(Suit.SPADES, Rank.SEVEN),
    /**
     * Ace of hearts
     */
    HA(Suit.HEARTS, Rank.ACE),
    /**
     * Ten of hearts
     */
    HT(Suit.HEARTS, Rank.TEN),
    /**
     * King of hearts
     */
    HK(Suit.HEARTS, Rank.KING),
    /**
     * Queen of hearts
     */
    HQ(Suit.HEARTS, Rank.QUEEN),
    /**
     * Jack of hearts
     */
    HJ(Suit.HEARTS, Rank.JACK),
    /**
     * Nine of hearts
     */
    H9(Suit.HEARTS, Rank.NINE),
    /**
     * Eight of hearts
     */
    H8(Suit.HEARTS, Rank.EIGHT),
    /**
     * Seven of hearts
     */
    H7(Suit.HEARTS, Rank.SEVEN),
    /**
     * Ace of diamonds
     */
    DA(Suit.DIAMONDS, Rank.ACE),
    /**
     * Ten of diamonds
     */
    DT(Suit.DIAMONDS, Rank.TEN),
    /**
     * King of diamonds
     */
    DK(Suit.DIAMONDS, Rank.KING),
    /**
     * Queen of diamonds
     */
    DQ(Suit.DIAMONDS, Rank.QUEEN),
    /**
     * Jack of diamonds
     */
    DJ(Suit.DIAMONDS, Rank.JACK),
    /**
     * Nine of diamonds
     */
    D9(Suit.DIAMONDS, Rank.NINE),
    /**
     * Eight of diamonds
     */
    D8(Suit.DIAMONDS, Rank.EIGHT),
    /**
     * Seven of diamonds
     */
    D7(Suit.DIAMONDS, Rank.SEVEN);

    public final Suit suit;
    public final Rank rank;

    Card(final Suit suit, final Rank rank) {

        this.suit = suit;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return suit.symbol + rank.symbol;
    }

    public enum Suit {
        /**
         * Club or Eichel
         */
        CLUBS("C", 3),
        /**
         * Spades or Grün
         */
        SPADES("S", 2),
        /**
         * Hearts or Herz
         */
        HEARTS("H", 1),
        /**
         * Diamonds or Schellen
         */
        DIAMONDS("D", 0);

        public final String symbol;
        public final int rank;

        Suit(final String symbol, final int rank) {
            this.symbol = symbol;
            this.rank = rank;
        }
    }

    public enum Rank {
        /**
         * Seven
         */
        SEVEN("7", 0, 0),
        /**
         * Eight
         */
        EIGHT("8", 0, 1),
        /**
         * Nine
         */
        NINE("9", 0, 2),
        /**
         * Jack or Unter
         */
        JACK("J", 2, 7),
        /**
         * Queen or Ober
         */
        QUEEN("Q", 3, 3),
        /**
         * King or König
         */
        KING("K", 4, 4),
        /**
         * Ten
         */
        TEN("T", 10, 5),
        /**
         * Ace or Daus
         */
        ACE("A", 11, 6);

        public final String symbol;
        public final int value;
        public final int suitGameRank;

        Rank(final String symbol, final int value, final int suitGameRank) {
            this.symbol = symbol;
            this.value = value;
            this.suitGameRank = suitGameRank;
        }
    }
}
