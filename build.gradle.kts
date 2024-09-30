plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.2"
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
    implementation(libs.bundles.cloud)
    annotationProcessor(libs.cloudannotations)
    implementation(libs.bundles.obliviate.invs)
    compileOnly(libs.paper.api)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    compileOnly(libs.placeholderapi)
    // provided by spigot library loader
    compileOnly(libs.mongodb.driver.sync)
    compileOnly(libs.sshj)

    // TESTS
    testImplementation(libs.sshj)
    testImplementation(libs.guava)
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.bundles.test)
    testCompileOnly(libs.lombok)
    testImplementation(libs.mongodb.driver.sync)
    testAnnotationProcessor(libs.lombok)
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }
    build {
        dependsOn("shadowJar")
    }

    shadowJar {

        //relocate("org.incendo.cloud", "de.kalypzo.command.cloud")
    }

    runServer {
        minecraftVersion("1.21.1")
    }

    processResources {
        filesMatching("plugin.yml") {
            expand(
                mapOf(
                    "version" to project.version.toString(),
                    "mongosync" to libs.mongodb.driver.sync.get(),
                    "sshj" to libs.sshj.get()
                )
            )
        }
    }
}

tasks.test {
    useJUnitPlatform()
}