package com.kotlin.aiblogdraft.api.domain.draft

import com.kotlin.aiblogdraft.storage.db.repository.DraftRepository
import org.springframework.stereotype.Component

@Component
class DraftReader(
    private val draftRepository: DraftRepository,
) {
    fun readAllByUserId(userId: Long) = draftRepository.findByUserIdOrderByCreatedAtDesc(userId)
}
