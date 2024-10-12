package com.kotlin.aiblogdraft.batch.job.draftContent

import com.kotlin.aiblogdraft.batch.domain.ai.AiDraftGenerator
import com.kotlin.aiblogdraft.storage.db.entity.DraftContentEntity
import com.kotlin.aiblogdraft.storage.db.entity.DraftImageGroupEntity
import com.kotlin.aiblogdraft.storage.db.repository.DraftImageRepository
import org.springframework.batch.item.ItemProcessor

class DraftContentItemProcessor(
    private val aiDraftGenerator: AiDraftGenerator,
    private val draftImageRepository: DraftImageRepository,
) : ItemProcessor<DraftImageGroupEntity, DraftContentEntity> {
    override fun process(item: DraftImageGroupEntity): DraftContentEntity? {
        val images = draftImageRepository.findAllByDraftImageGroupId(item.id)
        val content = aiDraftGenerator.generate(item.draft!!, images)

        return DraftContentEntity(
            content = content,
            imageGroupId = item.id,
        )
    }
}
