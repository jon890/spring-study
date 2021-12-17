import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"

    kotlin("jvm") version "1.6.0"
    kotlin("plugin.spring") version "1.6.0" // wrapped all-open
    kotlin("plugin.jpa") version "1.6.0" // wrapper no-arg
}

group "com.bifos"
version "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11
repositories {
    mavenCentral()
}

dependencies {
    // spring
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // hateoas
    implementation("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.springframework.hateoas:spring-hateoas")

    // openapi
    // todo kbt : version 은 외부에서 지정할 수 있게
    implementation("org.springdoc:springdoc-openapi-ui:1.6.1")
    // implementation("org.springdoc:springdoc-openapi-webmvc-core:1.6.1")
    // implementation("org.springdoc:springdoc-openapi-hateoas:1.6.1")
    // implementation("org.springdoc:springdoc-openapi-security:1.6.1")
    // implementation("org.springdoc:springdoc-openapi-kotlin:1.6.1")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // kotlin
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib"))
    implementation(kotlin("stdlib-jdk8"))

    // db
    runtimeOnly("com.h2database:h2")

    // dev only
    // spring dev tools
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // test
    // spring-test, json-path, junit, assertj-core, mockito-core, hamcrest-core, jsonassert 포함
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}