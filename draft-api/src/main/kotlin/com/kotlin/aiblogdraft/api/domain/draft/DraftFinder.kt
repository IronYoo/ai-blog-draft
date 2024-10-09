package com.kotlin.aiblogdraft.api.domain.draft

import com.kotlin.aiblogdraft.api.exception.DraftNotFoundException
import com.kotlin.aiblogdraft.api.exception.DraftUnAuthorizedException
import com.kotlin.aiblogdraft.storage.db.repository.DraftRepository
import com.kotlin.aiblogdraft.storage.db.repository.dto.FindWithRelationsResult
import org.springframework.stereotype.Component

@Component
class DraftFinder(
    private val draftRepository: DraftRepository,
) {
    fun findDetail(
        id: Long,
        userId: Long,
    ): FindWithRelationsResult {
        val found = draftRepository.findWithRelations(id) ?: throw DraftNotFoundException()
        if (found.draft.userId != userId) throw DraftUnAuthorizedException()

        return found
    }
}
