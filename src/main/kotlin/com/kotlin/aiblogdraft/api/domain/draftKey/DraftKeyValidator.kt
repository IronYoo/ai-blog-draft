package com.kotlin.aiblogdraft.api.domain.draftKey

import com.kotlin.aiblogdraft.api.exception.DraftKeyNotAllowedException
import com.kotlin.aiblogdraft.storage.db.entity.DraftKeyEntity
import org.springframework.stereotype.Component

@Component
class DraftKeyValidator {
    fun validate(
        draftKey: DraftKeyEntity,
        userId: Long,
    ) {
        if (draftKey.userId != userId) {
            throw DraftKeyNotAllowedException()
        }
    }
}
