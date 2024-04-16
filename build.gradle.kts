plugins {
    java
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "org.example"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    // TODO 1. spring sec:
    // makes everything "private" (which is good)
    // login with user & password (generated in console)
    implementation("org.springframework.boot:spring-boot-starter-security")
    // TODO 5. spring oauth2:
    // oauth2 for SSO
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    // spring:
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    // default:
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}