tasks.getByName("bootJar") {
    enabled = false
}

tasks.getByName("jar") {
    enabled = true
}

dependencies {
    implementation("io.awspring.cloud:spring-cloud-aws-starter-s3:3.2.0")
    implementation("org.springframework.boot:spring-boot-starter-web")
}
