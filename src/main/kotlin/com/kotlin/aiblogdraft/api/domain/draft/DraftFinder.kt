package com.kotlin.aiblogdraft.api.domain.draft

import com.kotlin.aiblogdraft.api.exception.DraftNotFoundException
import com.kotlin.aiblogdraft.api.exception.DraftUnAuthorizedException
import com.kotlin.aiblogdraft.storage.db.repository.DraftRepository
import com.kotlin.aiblogdraft.storage.db.repository.dto.DraftWithImageGroups
import org.springframework.stereotype.Component

@Component
class DraftFinder(
    private val draftRepository: DraftRepository,
) {
    fun findByIdWithImageGroups(
        id: Long,
        userId: Long,
    ): DraftWithImageGroups {
        val draftWithImageGroups = draftRepository.findByIdWithImageGroups(id) ?: throw DraftNotFoundException()
        if (draftWithImageGroups.draft.userId != userId) throw DraftUnAuthorizedException()

        return draftWithImageGroups
    }
}
