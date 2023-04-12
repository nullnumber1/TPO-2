plugins {
    id("java")
    id("jacoco")
}

group = "com.nullnumber1"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.commons:commons-csv:1.10.0")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:5.2.0")
}

tasks.test {
    useJUnitPlatform()
}

jacoco {
    toolVersion = "0.8.9" // Set the version you want to use
}

tasks.test {
    finalizedBy("jacocoTestReport")
}

tasks.withType<JacocoReport> {
    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.required.set(true)
    }
}