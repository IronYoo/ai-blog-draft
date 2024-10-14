tasks.getByName("bootJar") {
    enabled = false
}

tasks.getByName("jar") {
    enabled = true
}

dependencies {
    implementation(project(":cloud"))
    implementation("io.awspring.cloud:spring-cloud-aws-starter-s3:3.2.0")
}
