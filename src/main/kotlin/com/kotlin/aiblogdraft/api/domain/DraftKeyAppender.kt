package com.kotlin.aiblogdraft.api.domain

import com.kotlin.aiblogdraft.storage.db.repository.DraftKeyRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class DraftKeyAppender(
    private val draftKeyRepository: DraftKeyRepository,
) {
    fun appendKey(dto: AppendDraftKey): String {
        val key = UUID.randomUUID().toString()
        return draftKeyRepository.save(dto.toEntity(key)).key
    }
}
