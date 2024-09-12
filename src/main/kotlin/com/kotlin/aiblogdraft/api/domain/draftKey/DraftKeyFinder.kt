package com.kotlin.aiblogdraft.api.domain.draftKey

import com.kotlin.aiblogdraft.api.exception.DraftKeyNotFoundException
import com.kotlin.aiblogdraft.storage.db.entity.DraftKeyEntity
import com.kotlin.aiblogdraft.storage.db.repository.DraftKeyRepository
import org.springframework.stereotype.Component

@Component
class DraftKeyFinder(
    private val draftKeyRepository: DraftKeyRepository,
    private val draftKeyValidator: DraftKeyValidator,
) {
    fun getValidDraftKey(
        key: String,
        userId: Long,
    ): DraftKeyEntity {
        val draftKey = draftKeyRepository.findByKey(key) ?: throw DraftKeyNotFoundException()
        draftKeyValidator.validate(draftKey, userId)

        return draftKey
    }
}
