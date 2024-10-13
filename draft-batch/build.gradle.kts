plugins {
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

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
    implementation(project(":cloud:sqs"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-function-kotlin:3.2.8")
    implementation("org.springframework.cloud:spring-cloud-function-adapter-aws:3.2.8")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.boot:spring-boot-starter-batch")
    testImplementation("org.springframework.batch:spring-batch-test")

    implementation("org.springframework.ai:spring-ai-openai-spring-boot-starter")
    implementation("org.springframework.cloud:spring-cloud-function-web:4.1.3")
}

tasks.withType<Jar> {
    manifest {
        attributes["Start-Class"] = "com.kotlin.aiblogdraft.AiBlogDraftBatchApplicationKt"
    }
}

tasks.assemble {
    dependsOn("shadowJar")
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveClassifier.set("aws")
    archiveFileName.set("batch.jar")
    dependencies {
        exclude("org.springframework.cloud:spring-cloud-function-web")
    }
    mergeServiceFiles()
    append("META-INF/spring.handlers")
    append("META-INF/spring.schemas")
    append("META-INF/spring.tooling")
    append("META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports")
    append("META-INF/spring/org.springframework.boot.actuate.autoconfigure.web.ManagementContextConfiguration.imports")
    transform(com.github.jengelman.gradle.plugins.shadow.transformers.PropertiesFileTransformer::class.java) {
        paths.add("META-INF/spring.factories")
        mergeStrategy = "append"
    }
}
