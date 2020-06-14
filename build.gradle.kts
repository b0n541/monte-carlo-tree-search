/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java project to get you started.
 * For more details take a look at the Java Quickstart chapter in the Gradle
 * User Manual available at https://docs.gradle.org/6.5/userguide/tutorial_java_projects.html
 */

plugins {
    // Apply the java plugin to add support for Java
    java

    // Apply the application plugin to add support for building a CLI application.
    application
}

repositories {
    // Use jcenter for resolving dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
}

dependencies {
    // Guava
    implementation("com.google.guava:guava:29.0-jre")

    // Logging
    implementation("ch.qos.logback:logback-classic:1.2.3")

    // Test
    testImplementation("org.assertj:assertj-core:3.16.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.6.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter:5.6.2")
}

application {
    // Define the main class for the application.
    mainClassName = "org.b0n541.pmcts.App"
}

val test by tasks.getting(Test::class) {
    // Use junit platform for unit tests
    useJUnitPlatform()
}
