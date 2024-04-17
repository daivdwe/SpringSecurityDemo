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
    // makes everything "private" (which is good)
    // login with user & password (generated in console)
    //??: 1. spring sec:
    implementation("org.springframework.boot:spring-boot-starter-security")
    // oauth2 for SSO
    //??: 5. spring oauth2:
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