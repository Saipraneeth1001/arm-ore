plugins {
	id 'java'
	id 'org.springframework.boot' version '2.7.15'
	id 'io.spring.dependency-management' version '1.0.15.RELEASE'
}

group = 'com.arm'
version = '0.0.1-SNAPSHOT'

java {
	sourceCompatibility = '1.8'
}

configurations {
	compileOnly {
		extendsFrom annotationProcessor
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-web'
	compileOnly 'org.projectlombok:lombok'
	annotationProcessor 'org.projectlombok:lombok'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
}

tasks.named('test') {
	useJUnitPlatform()
}
