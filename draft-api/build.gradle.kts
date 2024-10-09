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
}
