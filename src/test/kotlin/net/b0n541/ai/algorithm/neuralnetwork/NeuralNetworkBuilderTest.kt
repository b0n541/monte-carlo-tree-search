package net.b0n541.ai.algorithm.neuralnetwork

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.nd4j.linalg.factory.Nd4j
import java.io.File

class NeuralNetworkBuilderTest {
    @Test
    fun initNetwork() {
        val network = createNeuralNetwork(listOf(2, 5, 2))
        println(network.summary())
        val output = network.output(Nd4j.zeros(1, 2))

        assertThat(output.getDouble(0)).isGreaterThanOrEqualTo(0.0)
        assertThat(output.getDouble(1)).isLessThanOrEqualTo(1.0)
    }

    @Test
    fun saveAndLoadNetwork() {
        val network = createNeuralNetwork(listOf(2, 5, 5, 2))
        saveNeuralNetwork(network, File("/tmp/asdf.net"))
        val savedNetwork = loadNeuralNetwork(File("/tmp/asdf.net"))

        assertThat(network.summary()).isEqualTo(savedNetwork.summary())
    }
}