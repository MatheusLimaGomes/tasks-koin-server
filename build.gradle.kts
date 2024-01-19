
val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

val koin_version: String by project

plugins {
    kotlin("jvm") version "1.9.22"
    id("io.ktor.plugin") version "2.2.4"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.10"
}

group = "br.com.tasks"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-call-logging-jvm")
    implementation("io.ktor:ktor-server-default-headers-jvm")
    implementation("io.ktor:ktor-server-host-common-jvm")
    implementation("io.ktor:ktor-server-status-pages-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")

    //Koin
    implementation("io.insert-koin:koin-ktor:$koin_version")
    implementation("io.insert-koin:koin-core:$koin_version")
    implementation("io.insert-koin:koin-logger-slf4j:$koin_version")

    //Mongodb-driver-kotlin
    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:4.11.0")

    // Kotlin-serialization
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")

    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    implementation(kotlin("script-runtime"))
}
