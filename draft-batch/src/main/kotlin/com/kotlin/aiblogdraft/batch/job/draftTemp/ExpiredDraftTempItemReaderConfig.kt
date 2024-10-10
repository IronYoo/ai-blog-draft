package com.kotlin.aiblogdraft.batch.job.draftTemp

import com.kotlin.aiblogdraft.storage.db.entity.DraftTempEntity
import com.kotlin.aiblogdraft.storage.db.repository.DraftTempRepository
import jakarta.persistence.EntityManager
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDateTime

@Configuration
class ExpiredDraftTempItemReaderConfig(
    private val entityManager: EntityManager,
) {
    val chunkSize = 500
    val targetDateTime: LocalDateTime = LocalDateTime.now().toLocalDate().atStartOfDay()

    @Bean
    fun expiredDraftTempJpaItemReader(draftTempRepository: DraftTempRepository): JpaPagingItemReader<DraftTempEntity> {
        val queryProvider = DestroyDraftTempJpaQueryProvider(targetDateTime)

        return JpaPagingItemReaderBuilder<DraftTempEntity>()
            .name("expiredDraftTempJpaItemReader")
            .entityManagerFactory(entityManager.entityManagerFactory)
            .pageSize(this.chunkSize)
            .queryProvider(queryProvider)
            .build()
    }
}
