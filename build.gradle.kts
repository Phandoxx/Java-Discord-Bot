plugins {
    id("java")
    id("application")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("org.ini4j:ini4j:0.5.4")
    implementation("org.slf4j:slf4j-api:2.0.12")
    implementation("org.slf4j:slf4j-simple:2.0.12") // ✅ for basic console logging
    implementation("net.dv8tion:JDA:5.0.0-beta.24")
}

tasks.test {
    useJUnitPlatform()
}
application {
    mainClass.set("org.javagui.Main")
}