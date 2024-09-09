package com.kotlin.aiblogdraft.api.domain.draft

import com.kotlin.aiblogdraft.storage.db.entity.DraftEntity

data class AppendDraft(
    val key: String,
    val type: DraftType,
    val title: String,
)

fun AppendDraft.toEntity() =
    DraftEntity(
        key = key,
        type = type.draftEntityType,
        title = title,
    )
