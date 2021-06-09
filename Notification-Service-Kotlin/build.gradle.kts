import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.0"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.10"
	kotlin("plugin.spring") version "1.5.10"
}

group = "byrd.riley"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

val twilioVersion = "8.14.0"
val springMockKVersion = "3.0.1"
val qpidBrokerVersion = "8.0.4"

repositories {
	mavenCentral()
}

dependencies {
	// Queuing
	implementation("com.rabbitmq:amqp-client")
	implementation("org.springframework.boot:spring-boot-starter-amqp")

	// S.M.S.
	implementation("com.twilio.sdk:twilio:${twilioVersion}")

	// Mail
	implementation("org.springframework.boot:spring-boot-starter-mail")

	// Kotlin
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	// Test Environment
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(module = "mockito-core")
	}
	testImplementation("org.springframework.amqp:spring-rabbit-test")
	testImplementation("com.ninja-squad:springmockk:${springMockKVersion}")
	testImplementation("org.apache.qpid:qpid-broker-core:${qpidBrokerVersion}")
	testImplementation("org.apache.qpid:qpid-broker-plugins-memory-store:${qpidBrokerVersion}")
	testImplementation("org.apache.qpid:qpid-broker-plugins-amqp-0-8-protocol:${qpidBrokerVersion}")
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
