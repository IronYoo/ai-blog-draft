package com.kotlin.aiblogdraft.batch.job.draftContent

import com.kotlin.aiblogdraft.batch.domain.ai.AiDraftGenerator
import com.kotlin.aiblogdraft.batch.domain.image.ImageResizer
import com.kotlin.aiblogdraft.external.cloudfront.CloudFrontProcessor
import com.kotlin.aiblogdraft.external.s3.S3Downloader
import com.kotlin.aiblogdraft.external.s3.S3Uploader
import com.kotlin.aiblogdraft.storage.db.entity.DraftContentEntity
import com.kotlin.aiblogdraft.storage.db.entity.DraftImageEntity
import com.kotlin.aiblogdraft.storage.db.entity.DraftImageGroupEntity
import com.kotlin.aiblogdraft.storage.db.repository.DraftContentRepository
import com.kotlin.aiblogdraft.storage.db.repository.DraftImageRepository
import com.kotlin.aiblogdraft.storage.db.repository.DraftRepository
import jakarta.persistence.EntityManager
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableBatchProcessing
@EnableTransactionManagement
class DraftContentJobConfig(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val entityManager: EntityManager,
    private val aiDraftGenerator: AiDraftGenerator,
    private val draftRepository: DraftRepository,
    private val draftImageRepository: DraftImageRepository,
    private val draftContentRepository: DraftContentRepository,
    private val s3Downloader: S3Downloader,
    private val imageResizer: ImageResizer,
    private val s3Uploader: S3Uploader,
    private val cloudFrontProcessor: CloudFrontProcessor,
) {
    val jobName = "draftContentJob"
    val chunkSize = 100

    @Bean
    fun draftContentJob(): Job =
        JobBuilder(jobName, jobRepository)
            .start(processingDraftStep())
            .next(draftContentStep())
            .next(doneDraftStep())
            .build()

    @Bean
    @JobScope
    fun processingDraftStep(): Step =
        StepBuilder(jobName + "_processingDraft_step", jobRepository)
            .chunk<DraftImageEntity, DraftImageEntity>(chunkSize, transactionManager)
            .reader(imageItemReader(null))
            .processor(imageResizeProcessor())
            .writer(imageResizeWriter())
            .build()

    @Bean
    @JobScope
    fun imageItemReader(
        @Value("#{jobParameters['draftId']}") draftId: String?,
    ): JpaPagingItemReader<DraftImageEntity> {
        val parsedDraftId = draftId!!.toLong()
        val queryProvider = ImageJpaQueryProvider(parsedDraftId)
        return JpaPagingItemReaderBuilder<DraftImageEntity>()
            .name("imageItemReader")
            .entityManagerFactory(entityManager.entityManagerFactory)
            .pageSize(chunkSize)
            .queryProvider(queryProvider)
            .build()
    }

    @Bean
    fun imageResizeProcessor() = ImageResizeProcessor(s3Downloader, imageResizer, s3Uploader, cloudFrontProcessor)

    @Bean
    fun imageResizeWriter() = ImageResizeWriter(draftImageRepository)

    @Bean
    @JobScope
    fun draftContentStep(): Step =
        StepBuilder(jobName + "_draftContent_step", jobRepository)
            .chunk<DraftImageGroupEntity, DraftContentEntity>(chunkSize, transactionManager)
            .reader(imageGroupItemReader(null))
            .processor(draftContentItemProcessor())
            .writer(draftContentItemWriter())
            .build()

    @Bean
    @JobScope
    fun imageGroupItemReader(
        @Value("#{jobParameters['draftId']}") draftId: String?,
    ): JpaPagingItemReader<DraftImageGroupEntity> {
        val parsedDraftId = draftId!!.toLong()
        val queryProvider = ImageGroupJpaQueryProvider(parsedDraftId)
        return JpaPagingItemReaderBuilder<DraftImageGroupEntity>()
            .name("imageGroupItemReader")
            .entityManagerFactory(entityManager.entityManagerFactory)
            .pageSize(chunkSize)
            .queryProvider(queryProvider)
            .build()
    }

    @Bean
    fun draftContentItemProcessor() = DraftContentItemProcessor(aiDraftGenerator, draftImageRepository)

    @Bean
    fun draftContentItemWriter() = DraftContentItemWriter(draftContentRepository)

    @Bean
    @JobScope
    fun doneDraftStep(): Step =
        StepBuilder(jobName + "_doneDraftStep_step", jobRepository)
            .tasklet(doneDraftTasklet(null), transactionManager)
            .build()

    @Bean
    @JobScope
    fun doneDraftTasklet(
        @Value("#{jobParameters['draftId']}") draftId: String?,
    ): Tasklet =
        Tasklet { _, _ ->
            val parsedDraftId = draftId!!.toLong()
            val draftEntity = draftRepository.findByIdOrNull(parsedDraftId) ?: throw IllegalArgumentException("존재하지 않는 초안입니다.")
            draftEntity.done()
            draftRepository.save(draftEntity)

            RepeatStatus.FINISHED
        }
}
