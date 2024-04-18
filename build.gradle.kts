plugins {
    java
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "sec"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    // Doc: https://docs.spring.io/spring-security
    // makes everything "private" without config (which is good)
    // login with user & password (generated in console)
    //??: 1. spring sec implementation
    implementation("org.springframework.boot:spring-boot-starter-security")
    // oauth2 for SSO
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    // Doc: https://docs.spring.io/spring-boot/
    // spring:
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    // guava:
    implementation("com.google.guava:guava:33.1.0-jre")
    // default:
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}