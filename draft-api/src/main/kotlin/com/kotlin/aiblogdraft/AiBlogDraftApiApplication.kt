package com.kotlin.aiblogdraft

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@SpringBootApplication
@EnableBatchProcessing
@ConfigurationPropertiesScan
class AiBlogDraftApiApplication

fun main(args: Array<String>) {
    runApplication<AiBlogDraftApiApplication>(*args)
}
