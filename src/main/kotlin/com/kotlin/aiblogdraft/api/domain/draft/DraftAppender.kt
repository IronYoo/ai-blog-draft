package com.kotlin.aiblogdraft.api.domain.draft

import com.kotlin.aiblogdraft.api.domain.draft.dto.AppendDraft
import com.kotlin.aiblogdraft.storage.db.entity.DraftEntity
import com.kotlin.aiblogdraft.storage.db.repository.DraftImageGroupRepository
import com.kotlin.aiblogdraft.storage.db.repository.DraftRepository
import com.kotlin.aiblogdraft.storage.db.repository.DraftTempRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DraftAppender(
    private val draftTempRepository: DraftTempRepository,
    private val draftImageGroupRepository: DraftImageGroupRepository,
    private val draftRepository: DraftRepository,
) {
    @Transactional
    fun append(
        tempId: Long,
        userId: Long,
        appendDraft: AppendDraft,
    ): DraftEntity {
        draftTempRepository.deleteById(tempId)
        val draft = draftRepository.save(appendDraft.toDraftEntity(userId))
        val imageGroups = draftImageGroupRepository.findAllByDraftTempId(tempId)
        imageGroups.forEach { it.updateDraftId(draft.id) }

        return draft
    }
}
