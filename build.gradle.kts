plugins {
    java
    id("org.springframework.boot") version "3.3.3"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "live.allone"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")
    implementation("org.postgresql:postgresql")
    implementation ("org.hibernate:hibernate-spatial:6.5.2.Final")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml")
    testImplementation ("io.rest-assured:rest-assured:5.4.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
