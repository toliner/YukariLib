buildscript {
    repositories {
        gradlePluginPortal()
        jcenter()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.3.30")
    }
}

val kotlinVersion: String by rootProject

plugins {
    id("kotlin-multiplatform") version "1.3.30"
    id("kotlinx-serialization") version "1.3.30"
}

repositories {
    mavenCentral()
    jcenter()
    maven(url = "https://kotlin.bintray.com/kotlinx")
}

dependencies {
    commonMainImplementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:0.11.0")
    commonMainImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:1.2.0")
    commonMainImplementation(kotlin("stdlib-common"))
}

group = "net.toliner"
version = "2.0.0"
