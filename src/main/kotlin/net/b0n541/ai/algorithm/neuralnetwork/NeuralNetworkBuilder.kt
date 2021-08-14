package net.b0n541.ai.algorithm.neuralnetwork

import org.deeplearning4j.nn.conf.NeuralNetConfiguration
import org.deeplearning4j.nn.conf.Updater
import org.deeplearning4j.nn.conf.layers.DenseLayer
import org.deeplearning4j.nn.conf.layers.OutputLayer
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.deeplearning4j.nn.weights.WeightInit
import org.nd4j.linalg.activations.Activation
import org.nd4j.linalg.lossfunctions.LossFunctions
import java.io.File

fun createNeuralNetwork(layers: List<Int>): MultiLayerNetwork {

    val neuralNetworkConfig = NeuralNetConfiguration.Builder()
        .updater(Updater.RMSPROP)
        .seed(1234567890)
        .list()

    for (i in layers.indices) {
        if (i < layers.size - 1) {
            neuralNetworkConfig.layer(denseLayer(layers[i], layers[i + 1]))
        } else {
            neuralNetworkConfig.layer(outputLayer(layers[i]))
        }
    }

    val network = MultiLayerNetwork(neuralNetworkConfig.build())
    network.init()

    return network
}

private fun denseLayer(inputs: Int, outputs: Int): DenseLayer {
    return DenseLayer.Builder()
        .nIn(inputs)
        .activation(Activation.RELU)
        .weightInit(WeightInit.XAVIER)
        .nOut(outputs)
        .build()
}

private fun outputLayer(outputs: Int): OutputLayer {
    return OutputLayer.Builder()
        .activation(Activation.SOFTMAX)
        .weightInit(WeightInit.XAVIER)
        .nOut(outputs)
        .lossFunction(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
        .build()
}

fun saveNeuralNetwork(network: MultiLayerNetwork, file: File) {
    network.save(file)
}

fun loadNeuralNetwork(file: File): MultiLayerNetwork {
    return MultiLayerNetwork.load(file, true)
}
