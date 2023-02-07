import com.google.protobuf.gradle.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.8"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("jvm") version "1.7.20"
    kotlin("plugin.spring") version "1.7.20"

    // protobuf plugin 설정
    id("com.google.protobuf") version "0.8.13"
}

group = "com.zayson.grpc"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

// protobuf 관리 및 stub 자동생성 라이브러리 의존성 추가
val grpcVersion = "3.21.9"
val grpcKotlinVersion = "1.3.0"
val grpcProtoVersion = "1.50.2"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // kotlin 로그찍기
    implementation("io.github.microutils:kotlin-logging-jvm:2.1.23")

    // gRPC + protobuf
    implementation("io.grpc:grpc-kotlin-stub:$grpcKotlinVersion")
    implementation("io.grpc:grpc-protobuf:$grpcProtoVersion")
    implementation("com.google.protobuf:protobuf-kotlin:$grpcVersion")
    implementation("com.google.protobuf:protobuf-java-util:3.21.10")
    implementation("io.grpc:grpc-stub:$grpcProtoVersion")
}

// build 후 Stub 클래스가 생성되는 타겟 설정
sourceSets {
    getByName("main") {
        java {
            srcDirs(
                "build/generated/source/proto/main/java",
                "build/generated/source/proto/main/kotlin",
                // 멀티모듈로 프로젝트 생성해서 path를 잡아줘야하는듯...
                "build/generated/source/proto/main/grpc",
                "build/generated/source/proto/main/grpckt"
            )
        }
    }
}


// build 시점에 protobuf를 생성하기 위한 task 추가
protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:$grpcVersion"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:$grpcProtoVersion"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:$grpcKotlinVersion:jdk8@jar"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
            }
            it.builtins {
                id("kotlin")
            }
        }
    }
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
