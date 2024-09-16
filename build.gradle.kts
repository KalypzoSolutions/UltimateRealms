plugins {
    id("java")
}

group = "de.kalypzo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("com.hierynomus:sshj:0.38.0")
    // TESTS
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("com.hierynomus:sshj:0.38.0")
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}