rootProject.name = "boosthemis"

plugins {
    val kotlinVersion = "1.8.10"
    kotlin("jvm") version kotlinVersion apply false
    kotlin("plugin.allopen") version kotlinVersion apply false
    kotlin("plugin.serialization") version kotlinVersion apply false


    // Apply the foojay-resolver plugin to allow automatic download of JDKs
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.4.0"
}

rootProject.name = "boosthemis"

include("service")
apply(from = "service/settings.gradle.kts")
