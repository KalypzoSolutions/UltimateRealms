plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

group = "de.kalypzo"
version = "0.2-DEV"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://jitpack.io")
}

dependencies {
    implementation("org.incendo:cloud-paper:2.0.0-SNAPSHOT")
    implementation("org.incendo:cloud-minecraft-extras:2.0.0-SNAPSHOT")
    implementation("org.incendo:cloud-annotations:2.0.0")
    implementation("com.github.hamza-cskn.obliviate-invs:core:4.3.0")
    implementation("com.github.hamza-cskn.obliviate-invs:pagination:4.3.0")
    implementation("com.github.hamza-cskn.obliviate-invs:advancedslot:4.3.0")
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
    compileOnly("com.hierynomus:sshj:0.38.0") // provided by spigot library loader
    compileOnly("org.projectlombok:lombok:1.18.34")
    compileOnly("org.mongodb:mongodb-driver-sync:5.1.4")
    annotationProcessor("org.projectlombok:lombok:1.18.34")
    compileOnly("me.clip:placeholderapi:2.11.6")
    // TESTS
    testImplementation("com.hierynomus:sshj:0.38.0")
    testImplementation("com.google.guava:guava:33.3.0-jre")
    testImplementation(platform("org.junit:junit-bom:5.11.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testCompileOnly("org.projectlombok:lombok:1.18.34")
    testImplementation("org.slf4j:slf4j-api:2.0.7")
    testImplementation("ch.qos.logback:logback-classic:1.4.8")
    testImplementation("org.mongodb:mongodb-driver-sync:5.1.4")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.34")


}

tasks.test {
    useJUnitPlatform()
}