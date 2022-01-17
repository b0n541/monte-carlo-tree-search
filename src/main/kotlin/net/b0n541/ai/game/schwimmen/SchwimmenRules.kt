package net.b0n541.ai.game.schwimmen

import net.b0n541.ai.game.common.card.Hand
import net.b0n541.ai.game.common.card.OpenCard
import net.b0n541.ai.game.common.card.Rank

fun getHandValue(hand: Hand): Double {
    val openCards = mutableListOf<OpenCard>()
    hand.cards.forEach {
        when (it) {
            is OpenCard -> openCards.add(it)
        }
    }

    return if (openCards.all { it.rank == Rank.ACE }) {
        33.0
    } else if (openCards.map { it.rank }.distinct().size == 1) {
        30.5
    } else {
        openCards.groupBy({ it.suit }, { it.rank })
            .mapValues { it.value.sumOf { rank -> getRankValue(rank) } }
            .maxOf { it.value }
    }
}

fun getCardValue(card: OpenCard): Double {
    return getRankValue(card.rank)
}

fun getRankValue(rank: Rank): Double {
    return when (rank) {
        Rank.ACE -> 11.0
        Rank.TEN,
        Rank.KING,
        Rank.QUEEN,
        Rank.JACK -> 10.0
        Rank.NINE -> 9.0
        Rank.EIGHT -> 8.0
        Rank.SEVEN -> 7.0
        else -> 0.0
    }
}