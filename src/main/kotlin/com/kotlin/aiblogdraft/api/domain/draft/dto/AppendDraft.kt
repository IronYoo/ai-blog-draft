package com.kotlin.aiblogdraft.api.domain.draft.dto

import com.kotlin.aiblogdraft.storage.db.entity.DraftEntity

data class AppendDraft(
    val type: DraftType,
    val title: String,
) {
    fun toDraftEntity(userId: Long) =
        DraftEntity(
            type = type.draftEntityType,
            title = title,
            userId = userId,
        )
}
