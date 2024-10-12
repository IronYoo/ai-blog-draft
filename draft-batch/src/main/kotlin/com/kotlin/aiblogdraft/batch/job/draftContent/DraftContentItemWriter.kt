package com.kotlin.aiblogdraft.batch.job.draftContent

import com.kotlin.aiblogdraft.storage.db.entity.DraftContentEntity
import com.kotlin.aiblogdraft.storage.db.repository.DraftContentRepository
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class DraftContentItemWriter(
    private val draftContentRepository: DraftContentRepository,
) : ItemWriter<DraftContentEntity> {
    override fun write(chunk: Chunk<out DraftContentEntity>) {
        for (item in chunk.items) {
            draftContentRepository.save(item)
        }
    }
}
