package net.b0n541.pmcts.game.skat

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class TrickTest {
    @Test
    fun newTrick() {
        val trick = Trick(PlayerPosition.FOREHAND)

        assertThat(trick.isFinished).isFalse()
        assertThat(trick.trickWinner).isNull()
    }

    @Test
    fun addCards() {
        val trick = Trick(PlayerPosition.FOREHAND)

        trick.addMove(SkatGameMove(PlayerPosition.FOREHAND, Card.CJ))

        assertThat(trick.isFinished).isFalse()
        assertThat(trick.trickWinner).isNull()

        trick.addMove(SkatGameMove(PlayerPosition.MIDDLEHAND, Card.SJ))

        assertThat(trick.isFinished).isFalse()
        assertThat(trick.trickWinner).isNull()

        trick.addMove(SkatGameMove(PlayerPosition.REARHAND, Card.HJ))

        assertThat(trick.isFinished).isTrue()
        assertThat(trick.trickWinner).isEqualTo(PlayerPosition.FOREHAND)
    }
}