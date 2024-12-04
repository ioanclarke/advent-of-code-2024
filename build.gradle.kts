plugins {
    kotlin("jvm") version "2.0.21"
    application
}

group = "ioan"
version = "1.0"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("ioan.MainKt")
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
