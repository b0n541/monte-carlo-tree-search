import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // Apply the java plugin to add support for Java
    java

    // Apply the application plugin to add support for building a CLI application.
    application

    kotlin("jvm") version "1.4.21"
}

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    // Guava
    implementation("com.google.guava:guava:30.1-jre")

    // Logging
    implementation("ch.qos.logback:logback-classic:1.2.3")

    // Test
    testRuntimeOnly("org.junit.jupiter:junit-jupiter:5.7.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.7.0")
    testImplementation("org.assertj:assertj-core:3.18.1")
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
