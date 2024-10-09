tasks.getByName("bootJar") {
    enabled = false
}

tasks.getByName("jar") {
    enabled = true
}

dependencies {
    api("io.github.oshai:kotlin-logging-jvm:7.0.0")
}
