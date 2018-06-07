import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    var kotlin_version: String by extra
    kotlin_version = "1.2.41"

    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(kotlinModule("gradle-plugin", kotlin_version))
    }
}

plugins {
    java
    application
    id("org.jetbrains.kotlin.jvm") version "1.2.41"
    id ("com.github.johnrengelman.shadow") version "2.0.4"
    id("io.spring.dependency-management") version "1.0.5.RELEASE"
}

group = "test"
version = "1.0-SNAPSHOT"

apply {
    plugin("kotlin")
}

val kotlin_version: String by extra

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlinModule("stdlib-jdk8", kotlin_version))
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:2.0.2.RELEASE")
    }
}

dependencies {
    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compile("org.jetbrains.kotlin:kotlin-reflect")

    compile("org.springframework:spring-webflux")
    compile("org.springframework:spring-test")
    compile("org.springframework:spring-context") {
        exclude(module = "spring-aop")
    }
    compile("io.projectreactor.ipc:reactor-netty")
    compile("com.samskivert:jmustache")

    compile("org.slf4j:slf4j-api")
    compile("ch.qos.logback:logback-classic")

    compile("com.fasterxml.jackson.module:jackson-module-kotlin")
    compile("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

    testCompile("io.projectreactor:reactor-test")
    testCompile("org.assertj:assertj-core")

    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}
