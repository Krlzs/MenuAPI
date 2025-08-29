plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.10"
    id("com.gradleup.shadow") version "8.3.0"
    id("xyz.jpenilla.run-paper") version "2.3.1"
}


group = "me.botxy2z"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") {
        name = "spigotmc-repo"
    }
    maven("https://oss.sonatype.org/content/repositories/snapshots/") {
        name = "sonatype-oss-snapshots"
    }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

tasks {
    runServer {
        minecraftVersion("1.8.8")
    }
}

val targetJavaVersion = 8
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks.shadowJar {
    archiveBaseName.set("MenuAPI")
    archiveVersion.set("")
    archiveClassifier.set("")
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}
