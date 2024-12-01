plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.25"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.9.25"
    id("com.google.devtools.ksp") version "1.9.25-1.0.20"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.micronaut.application") version "4.4.4"
    kotlin("plugin.jpa") version "1.9.25"
    id("com.diffplug.spotless") version "6.25.0"
}

version = "0.1"
group = "com.event"

val kotlinVersion = project.properties.get("kotlinVersion")
val micronautVersion = project.properties.get("micronautVersion")
val kotestVersion = project.properties.get("kotestVersion")
repositories {
    mavenCentral()
}

dependencies {

    ksp("io.micronaut:micronaut-http-validation")
    ksp("io.micronaut.serde:micronaut-serde-processor")
    ksp("io.micronaut.openapi:micronaut-openapi")
    implementation("io.micronaut:micronaut-http-server-netty")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    compileOnly("io.micronaut:micronaut-http-client")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    testImplementation("io.micronaut:micronaut-http-client")

    // Flyway migrations
    implementation("io.micronaut.flyway:micronaut-flyway")
    runtimeOnly("org.flywaydb:flyway-database-postgresql")

    // JPA
    implementation("io.micronaut.data:micronaut-data-hibernate-jpa")
    implementation("io.micronaut.sql:micronaut-hibernate-jpa")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.yaml:snakeyaml:2.0")

    implementation("io.micronaut.data:micronaut-data-processor")
    implementation("io.micronaut.data:micronaut-data-jpa")
    implementation("jakarta.persistence:jakarta.persistence-api")

    // OpenAPI
    ksp("io.micronaut.openapi:micronaut-openapi-annotations")
    annotationProcessor("io.micronaut.openapi:micronaut-openapi")
    implementation("io.swagger.core.v3:swagger-annotations")

    annotationProcessor("io.micronaut:micronaut-inject-java:$micronautVersion")
    ksp("io.micronaut:micronaut-aop:$micronautVersion")

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.mockk:mockk:1.12.0")
}

application {
    mainClass = "com.event.Application"
}
java {
    sourceCompatibility = JavaVersion.toVersion("21")
}

graalvmNative.toolchainDetection = false

micronaut {
    runtime("netty")
    processing {
        incremental(true)
        annotations("com.event.*")
    }
}

spotless {
    kotlin {
        ktlint("1.2.1")
        targetExclude("**/build/generated/**")
    }
    kotlinGradle {
        ktlint("1.2.1")
    }
    java {
        googleJavaFormat()
        removeUnusedImports()
        formatAnnotations()
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
