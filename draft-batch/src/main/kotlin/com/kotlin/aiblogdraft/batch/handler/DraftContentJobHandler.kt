package com.kotlin.aiblogdraft.batch.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.kotlin.aiblogdraft.cloud.sqs.message.DraftContentJobMessage
import com.kotlin.aiblogdraft.cloud.sqs.message.SqsMessage
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
            val payload = objectMapper.readValue(message, SqsMessage::class.java)
            log.info { "payload=$payload" }
            val body = objectMapper.readValue(payload.records[0].body, DraftContentJobMessage::class.java)
            log.info { "body=$body" }

            val parameters = JobParametersBuilder().addString("draftId", body.draftId.toString()).toJobParameters()
            val jobExecution = jobLauncher.run(draftContentJob, parameters)
            log.info { "Job Execution Status: ${jobExecution.status}" }
        }
}
