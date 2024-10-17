package com.kotlin.aiblogdraft.batch.job.draftContent

import com.kotlin.aiblogdraft.storage.db.entity.DraftImageEntity
import com.kotlin.aiblogdraft.storage.db.repository.DraftImageRepository
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class ImageResizeWriter(
    private val draftImageRepository: DraftImageRepository,
) : ItemWriter<DraftImageEntity> {
    override fun write(chunk: Chunk<out DraftImageEntity>) {
        for (item in chunk.items) {
            draftImageRepository.save(item)
        }
    }
}
