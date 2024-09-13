package com.kotlin.aiblogdraft.api.domain.draftKey.dto

import com.kotlin.aiblogdraft.storage.db.entity.DraftKeyEntity

data class AppendDraftKey(
    val userId: Long,
) {
    fun toDraftKeyEntity(key: String) =
        DraftKeyEntity(
            key = key,
            userId = userId,
        )
}
