package com.kotlin.aiblogdraft

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@EnableBatchProcessing
@SpringBootApplication
class AiBlogDraftBatchApplication

fun main(args: Array<String>) {
    runApplication<AiBlogDraftBatchApplication>(*args)
}
