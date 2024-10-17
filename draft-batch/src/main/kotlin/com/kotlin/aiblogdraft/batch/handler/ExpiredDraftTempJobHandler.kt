package com.kotlin.aiblogdraft.batch.handler

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class ExpiredDraftTempJobHandler(
    private val jobLauncher: JobLauncher,
    private val expiredDraftTempJob: Job,
) {
    private val log = KotlinLogging.logger {}

    @Bean
    fun expiredDraftTempJobLauncher(): (String) -> Unit =
        {
            val jobExecution = jobLauncher.run(expiredDraftTempJob, JobParametersBuilder().toJobParameters())
            log.info { "Job Execution Status: ${jobExecution.status}" }
        }
}
