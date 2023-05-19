pluginManagement {
    val quarkusPluginId = "io.quarkus"
    val quarkusPluginVersion = "3.0.3.Final"
    repositories {
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
    }
    plugins {
        id(quarkusPluginId) version quarkusPluginVersion
    }
}

