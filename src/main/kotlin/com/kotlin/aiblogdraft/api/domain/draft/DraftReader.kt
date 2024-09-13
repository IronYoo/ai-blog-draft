package com.kotlin.aiblogdraft.api.domain.draft

import com.kotlin.aiblogdraft.api.exception.DraftIsNotDone
import com.kotlin.aiblogdraft.api.exception.DraftNotFoundException
import com.kotlin.aiblogdraft.storage.db.entity.DraftEntity
import com.kotlin.aiblogdraft.storage.db.repository.DraftRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class DraftReader(
    private val draftRepository: DraftRepository,
) {
    fun readByUserId(userId: Long) = draftRepository.findByUserId(userId)

    fun readById(id: Long) = draftRepository.findByIdOrNull(id) ?: throw DraftNotFoundException()

    fun readDoneById(id: Long): DraftEntity {
        val draft = readById(id)
        if (!DraftStatus.isDone(draft.status)) {
            throw DraftIsNotDone()
        }

        return draft
    }
}
