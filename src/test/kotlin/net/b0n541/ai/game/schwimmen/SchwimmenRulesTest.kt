package net.b0n541.ai.game.schwimmen

import net.b0n541.ai.game.common.card.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class SchwimmenRulesTest {
    @Test
    fun multipleSuits() {
        assertThat(getHandValue(Hand(listOf(HQ, HT, DJ)))).isEqualTo(20.0)
    }

    @Test
    fun sameSuit() {
        assertThat(getHandValue(Hand(listOf(CA, CK, C9)))).isEqualTo(30.0)
    }

    @Test
    fun thirteeAndAHalf() {
        assertThat(getHandValue(Hand(listOf(D7, H7, S7)))).isEqualTo(30.5)
    }

    @Test
    fun thirteeOne() {
        assertThat(getHandValue(Hand(listOf(DA, DK, DT)))).isEqualTo(31.0)
    }

    @Test
    fun fire() {
        assertThat(getHandValue(Hand(listOf(CA, DA, HA)))).isEqualTo(33.0)
    }
}