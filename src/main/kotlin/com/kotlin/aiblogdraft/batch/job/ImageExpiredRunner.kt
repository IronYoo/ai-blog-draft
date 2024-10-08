package com.kotlin.aiblogdraft.batch.job

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.stereotype.Component
import kotlin.jvm.Throws

@Component
class ImageExpiredRunner(
    private val jobLauncher: JobLauncher,
    private val imageExpiredJob: Job,
) {
    private val log = KotlinLogging.logger {}

    @Throws(Exception::class)
    fun runJob() {
        val jobExecution = jobLauncher.run(imageExpiredJob, JobParameters())
        log.info { "Job Execution: ${jobExecution.status}" }
    }
}
