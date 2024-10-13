package com.kotlin.aiblogdraft.batch.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.kotlin.sqs.message.DraftContentJobMessage
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class DraftContentJobHandler(
    private val objectMapper: ObjectMapper,
    private val jobLauncher: JobLauncher,
    private val draftContentJob: Job,
) {
    private val log = KotlinLogging.logger {}

    @Bean
    fun draftContentJobLauncher(): (String) -> Unit =
        { message ->
            val payload = objectMapper.readValue(message, DraftContentJobMessage::class.java)
            log.info { "message=$payload" }

            val parameters = JobParametersBuilder().addString("draftId", payload.draftId.toString()).toJobParameters()
            val jobExecution = jobLauncher.run(draftContentJob, parameters)
            log.info { "Job Execution Status: ${jobExecution.status}" }
        }
}
