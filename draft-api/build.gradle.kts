tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}

dependencies {
    implementation(project(":storage"))
    implementation(project(":logging"))
    implementation(project(":cloud"))
    implementation(project(":cloud:image"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    testImplementation("io.awspring.cloud:spring-cloud-aws-starter-s3:3.2.0")
}
