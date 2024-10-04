package com.kotlin.aiblogdraft.api.domain.draft

import com.kotlin.aiblogdraft.api.domain.ai.AiDraftGenerator
import com.kotlin.aiblogdraft.api.exception.DraftNotFoundException
import com.kotlin.aiblogdraft.storage.db.entity.DraftContentEntity
import com.kotlin.aiblogdraft.storage.db.entity.DraftEntity
import com.kotlin.aiblogdraft.storage.db.repository.DraftContentRepository
import com.kotlin.aiblogdraft.storage.db.repository.DraftRepository
import org.springframework.stereotype.Component

@Component
class DraftProcessor(
    private val draftRepository: DraftRepository,
    private val draftContentRepository: DraftContentRepository,
    private val aiDraftGenerator: AiDraftGenerator,
) {
    private fun ready(draft: DraftEntity) {
        draft.process()
        draftRepository.save(draft)
    }

    private fun writeContent(
        imageGroupId: Long,
        content: String,
    ) {
        draftContentRepository.save(DraftContentEntity(content, imageGroupId))
    }

    private fun end(draft: DraftEntity) {
        draft.done()
        draftRepository.save(draft)
    }

    fun process(draftId: Long) {
        val draftWithImageGroups = draftRepository.findByIdWithImageGroups(draftId) ?: throw DraftNotFoundException()
        val draft = draftWithImageGroups.draft
        val imageGroups = draftWithImageGroups.groups

        ready(draftWithImageGroups.draft)
        imageGroups.forEach {
            val content = aiDraftGenerator.generate(draft, it)
            writeContent(it.id, content)
        }
        end(draft)
    }
}
