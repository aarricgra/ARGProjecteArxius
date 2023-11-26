plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    //jackson para xml y json
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.16.0")
    implementation("com.fasterxml.jackson.core:jackson-core:2.16.0-rc1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.16.0-rc1")

    // https://mvnrepository.com/artifact/com.networknt/json-schema-validator
    // validar schema
    implementation("com.networknt:json-schema-validator:1.0.87")

    // https://mvnrepository.com/artifact/org.apache.commons/commons-configuration2
    // leer ini
    implementation("org.apache.commons:commons-configuration2:2.9.0")

    // https://mvnrepository.com/artifact/org.thymeleaf/thymeleaf
    //plantillas html
    implementation("org.thymeleaf:thymeleaf:3.1.2.RELEASE")

    // https://mvnrepository.com/artifact/com.rometools/rome
    // hacer rss
    implementation("com.rometools:rome:2.1.0")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}