plugins {
    idea
    java
}

group = "de.torui.coflsky-core"
version = "1.5.5-alpha"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(8))
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.8.6")
    implementation("com.neovisionaries:nv-websocket-client:2.14")

}

tasks.test {
    useJUnitPlatform()
}