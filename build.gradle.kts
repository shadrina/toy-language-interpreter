plugins {
    java
}

group = "ru.nsu.shadrina"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.projectlombok", "lombok", "1.18.10")
    annotationProcessor("org.projectlombok", "lombok", "1.18.10")
    implementation("commons-cli", "commons-cli", "1.4")
    implementation("org.antlr", "antlr4-runtime", "4.7.2")
    implementation("org.ow2.asm", "asm", "7.1")
    testImplementation("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
}