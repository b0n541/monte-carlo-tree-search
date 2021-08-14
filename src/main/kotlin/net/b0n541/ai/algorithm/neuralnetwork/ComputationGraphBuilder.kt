package net.b0n541.ai.algorithm.neuralnetwork

import org.deeplearning4j.nn.conf.NeuralNetConfiguration
import org.deeplearning4j.nn.conf.Updater
import org.deeplearning4j.nn.conf.inputs.InputType
import org.deeplearning4j.nn.conf.layers.DenseLayer
import org.deeplearning4j.nn.conf.layers.OutputLayer
import org.deeplearning4j.nn.graph.ComputationGraph
import org.deeplearning4j.nn.weights.WeightInit
import org.nd4j.linalg.activations.Activation
import org.nd4j.linalg.lossfunctions.LossFunctions
import java.io.File

fun createComputationGraph(inputs: Int, hiddenLayers: List<Int>, outputs: List<Int>): ComputationGraph {

    val inputLayer = "inputs"
    val policyHead = "policy"
    val valueHead = "value"
    val computationGraphConfig = NeuralNetConfiguration.Builder()
        .updater(Updater.RMSPROP)
        .graphBuilder()
        .addInputs(inputLayer)
        .setInputTypes(InputType.feedForward(inputs.toLong()))
        .setOutputs(policyHead, valueHead)

    for (i in hiddenLayers.indices) {
        if (i < hiddenLayers.size) {
            if (i == 0) {
                computationGraphConfig.addLayer("layer-$i", denseLayer(inputs, hiddenLayers[i]), inputLayer)
            } else {
                val previousLayer = "layer-" + (i - 1)
                computationGraphConfig.addLayer(
                    "layer-$i",
                    denseLayer(hiddenLayers[i - 1], hiddenLayers[i]),
                    previousLayer
                )
            }
        }
    }

    computationGraphConfig.addLayer(
        policyHead,
        outputLayer(hiddenLayers.last(), Activation.SOFTMAX, outputs[0]),
        "layer-" + (hiddenLayers.size - 1)
    )
    computationGraphConfig.addLayer(
        valueHead,
        outputLayer(hiddenLayers.last(), Activation.TANH, outputs[1]),
        "layer-" + (hiddenLayers.size - 1)
    )

    val graph = ComputationGraph(computationGraphConfig.build())
    graph.init()

    return graph
}

private fun denseLayer(inputs: Int, outputs: Int): DenseLayer {
    return DenseLayer.Builder()
        .nIn(inputs)
        .activation(Activation.RELU)
        .weightInit(WeightInit.XAVIER)
        .nOut(outputs)
        .build()
}

private fun outputLayer(inputs: Int, activation: Activation, outputs: Int): OutputLayer {
    val builder = OutputLayer.Builder()
        .nIn(inputs)
        .weightInit(WeightInit.XAVIER)
        .activation(activation)
        .nOut(outputs)

    if (activation == Activation.TANH) {
        builder.lossFunction(LossFunctions.LossFunction.MEAN_ABSOLUTE_ERROR)
    }

    return builder.build()
}

fun saveComputationGraph(network: ComputationGraph, file: File) {
    network.save(file)
}

fun loadComputationGraph(file: File): ComputationGraph {
    return ComputationGraph.load(file, true)
}
