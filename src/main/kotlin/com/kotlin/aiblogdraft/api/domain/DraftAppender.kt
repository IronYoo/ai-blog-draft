package com.kotlin.aiblogdraft.api.domain

import com.kotlin.aiblogdraft.storage.db.entity.DraftEntity
import com.kotlin.aiblogdraft.storage.db.repository.DraftKeyRepository
import com.kotlin.aiblogdraft.storage.db.repository.DraftRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DraftAppender(
    private val draftKeyRepository: DraftKeyRepository,
    private val draftRepository: DraftRepository,
) {
    @Transactional
    fun append(appendDraft: AppendDraft): DraftEntity {
        draftKeyRepository.deleteById(appendDraft.key)
        return draftRepository.save(appendDraft.toEntity())
    }
}
