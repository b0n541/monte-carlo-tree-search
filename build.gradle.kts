import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // Apply the java plugin to add support for Java
    java

    // Apply the application plugin to add support for building a CLI application.
    application

    kotlin("jvm") version "2.1.10"
}

repositories {
    jcenter()
    maven {
        url = uri("https://repo.maven.apache.org/maven2")
        name = "Maven Central"
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.1.20")

    // Deeplearning4J
    implementation("org.deeplearning4j:deeplearning4j-core:1.0.0-M2.1")
    implementation("org.nd4j:nd4j-native-platform:1.0.0-M2.1")

    // Logging
    implementation("ch.qos.logback:logback-classic:1.5.18")

    // Test
    testRuntimeOnly("org.junit.jupiter:junit-jupiter:5.12.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.12.1")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.12.1")
    testImplementation("org.assertj:assertj-core:3.27.3")
}

application {
    mainClass.set("net.b0n541.ai.App")
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "17"
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "17"
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform()
}

val wrapper: Wrapper by tasks
wrapper.gradleVersion = "8.6"