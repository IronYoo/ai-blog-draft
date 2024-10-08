package com.kotlin.aiblogdraft.batch.job

import com.kotlin.aiblogdraft.storage.db.entity.DraftTempEntity
import com.kotlin.aiblogdraft.storage.db.repository.DraftTempRepository
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter
import org.springframework.lang.NonNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class DraftTempRemoveWriter(
    private val draftTempRepository: DraftTempRepository,
) : ItemWriter<DraftTempEntity> {
    override fun write(
        @NonNull chunk: Chunk<out DraftTempEntity>,
    ) {
        for (item in chunk.items) {
            draftTempRepository.delete(item)
        }
    }
}
