package com.kotlin.aiblogdraft.batch.job.draftTemp

import com.kotlin.storage.db.entity.DraftTempEntity
import com.kotlin.storage.db.repository.DraftTempRepository
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableBatchProcessing
@EnableTransactionManagement
class ImageExpiredJobConfig(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    @Qualifier("expiredDraftTempJpaItemReader") private val expiredDraftTempItemReader: JpaPagingItemReader<DraftTempEntity>,
    private val draftTempRepository: DraftTempRepository,
) {
    val jobName = "imageExpireJob"
    val chunkSize = 500

    @Bean
    fun imageExpiredJob(): Job =
        JobBuilder(jobName, jobRepository)
            .start(imageExpiredStep())
            .build()

    @Bean
    @JobScope
    fun imageExpiredStep(): Step =
        StepBuilder(jobName + "_step", jobRepository)
            .chunk<DraftTempEntity, DraftTempEntity>(chunkSize, transactionManager)
            .reader(expiredDraftTempItemReader)
            .writer(draftTempRemoveWriter())
            .build()

    @Bean
    fun draftTempRemoveWriter() = DraftTempRemoveWriter(draftTempRepository)
}
