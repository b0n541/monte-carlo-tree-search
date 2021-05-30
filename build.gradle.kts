import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // Apply the java plugin to add support for Java
    java

    // Apply the application plugin to add support for building a CLI application.
    application

    kotlin("jvm") version "1.5.0"
}

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0-native-mt")

    // Deeplearning4J
    implementation("org.deeplearning4j:deeplearning4j-core:1.0.0-M1")
    implementation("org.nd4j:nd4j-native-platform:1.0.0-M1")

    // Logging
    implementation("ch.qos.logback:logback-classic:1.2.3")

    // Test
    testRuntimeOnly("org.junit.jupiter:junit-jupiter:5.7.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.7.2")
    testImplementation("org.assertj:assertj-core:3.19.0")
}

application {
    mainClass.set("org.b0n541.ai.App")
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "11"
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "11"
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform()
}
