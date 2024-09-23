package com.kotlin.aiblogdraft

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@SpringBootApplication
class AiBlogDraftApiApplication

fun main(args: Array<String>) {
    runApplication<AiBlogDraftApiApplication>(*args)
}
