package net.b0n541.ai.algorithm.neuralnetwork

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.nd4j.linalg.factory.Nd4j
import java.io.File

class ComputationGraphBuilderTest {
    @Test
    fun initGraph() {
        val graph = createComputationGraph(19, listOf(256, 256, 256), listOf(9, 1))
        println(graph.summary())

        val output = graph.output(Nd4j.zeros(1, 19))

        println(output[0])
        println(output[1])

        assertThat(output[0].getDouble(0)).isGreaterThanOrEqualTo(0.0)
    }

    @Test
    fun saveAndLoadGraph() {
        val graph = createComputationGraph(19, listOf(256, 256, 256), listOf(9, 1))
        saveComputationGraph(graph, File("/tmp/asdf.net"))
        val savedGraph = loadComputationGraph(File("/tmp/asdf.net"))

        assertThat(graph.summary()).isEqualTo(savedGraph.summary())
    }
}