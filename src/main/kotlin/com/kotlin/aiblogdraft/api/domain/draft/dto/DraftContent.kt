package com.kotlin.aiblogdraft.api.domain.draft.dto

import com.kotlin.aiblogdraft.storage.db.entity.DraftEntity
import java.time.LocalDateTime

data class DraftContent(
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun fromDraftEntity(entity: DraftEntity): DraftContent =
            DraftContent(
                title = entity.title,
                content = entity.content!!,
                createdAt = entity.updatedAt,
            )
    }
}
