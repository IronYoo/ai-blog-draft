package com.kotlin.aiblogdraft.batch.job.draftContent

import com.kotlin.aiblogdraft.storage.db.entity.DraftEntity
import com.kotlin.aiblogdraft.storage.db.repository.DraftRepository
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class ProcessingDraftItemWriter(
    private val draftRepository: DraftRepository,
) : ItemWriter<DraftEntity> {
    override fun write(chunk: Chunk<out DraftEntity>) {
        for (item in chunk.items) {
            item.process()
            draftRepository.save(item)
        }
    }
}
