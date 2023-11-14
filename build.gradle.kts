plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-core:2.16.0-rc1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.16.0-rc1")
    implementation("com.networknt:json-schema-validator:1.0.87")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}