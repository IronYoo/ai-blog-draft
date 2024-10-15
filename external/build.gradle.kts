tasks.getByName("bootJar") {
    enabled = false
}

tasks.getByName("jar") {
    enabled = true
}

dependencies {
    implementation("io.awspring.cloud:spring-cloud-aws-starter-s3:3.2.0")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation(platform("io.awspring.cloud:spring-cloud-aws-dependencies:3.0.4"))
    implementation("io.awspring.cloud:spring-cloud-aws-starter-sqs")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
}
