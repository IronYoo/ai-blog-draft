package com.kotlin.aiblogdraft.api.domain.draftKey

import com.kotlin.aiblogdraft.storage.db.entity.DraftKeyEntity

data class AppendDraftKey(
    val userId: Long,
)

fun AppendDraftKey.toEntity(key: String) =
    DraftKeyEntity(
        key = key,
        userId = userId,
    )
