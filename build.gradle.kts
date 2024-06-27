plugins {
    id("java")
    kotlin("jvm")
}

group = "kr.co._29cm"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testRuntimeOnly("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.assertj:assertj-core:3.26.0")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnitPlatform()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

kotlin {
    jvmToolchain(17)
}