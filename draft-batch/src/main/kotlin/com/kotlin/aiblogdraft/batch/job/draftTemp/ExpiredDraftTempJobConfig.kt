package com.kotlin.aiblogdraft.batch.job.draftTemp

import com.kotlin.aiblogdraft.storage.db.entity.DraftTempEntity
import com.kotlin.aiblogdraft.storage.db.repository.DraftTempRepository
import jakarta.persistence.EntityManager
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Configuration
@EnableBatchProcessing
@EnableTransactionManagement
class ExpiredDraftTempJobConfig(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val entityManager: EntityManager,
    private val draftTempRepository: DraftTempRepository,
) {
    val jobName = "expiredDraftTempJob"
    val chunkSize = 500

    @Bean("expiredDraftTempJob")
    fun expiredDraftTempJob(): Job =
        JobBuilder(jobName, jobRepository)
            .start(imageExpiredStep())
            .build()

    @Bean
    @JobScope
    fun imageExpiredStep(): Step =
        StepBuilder(jobName + "_step", jobRepository)
            .chunk<DraftTempEntity, DraftTempEntity>(
                chunkSize,
                transactionManager,
            ).reader(expiredDraftTempItemReader(null))
            .writer(expiredDraftTempItemWriter())
            .build()

    @Bean
    @JobScope
    fun expiredDraftTempItemReader(
        @Value("#{jobParameters['requestDate']}") requestDate: String?,
    ): JpaPagingItemReader<DraftTempEntity> {
        val parsedDateTime = LocalDate.parse(requestDate!!, DateTimeFormatter.ofPattern("yyyyMMdd"))
        val targetDateTime = parsedDateTime.plusDays(1).atStartOfDay() // 여기서 날짜 계산
        val queryProvider = ExpiredDraftTempJpaQueryProvider(targetDateTime)
        return JpaPagingItemReaderBuilder<DraftTempEntity>()
            .name("expiredDraftTempItemReader")
            .entityManagerFactory(entityManager.entityManagerFactory)
            .pageSize(this.chunkSize)
            .queryProvider(queryProvider)
            .build()
    }

    @Bean
    fun expiredDraftTempItemWriter() = ExpiredDraftTempItemWriter(draftTempRepository)
}
