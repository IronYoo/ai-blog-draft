package com.kotlin.aiblogdraft.api.domain.draft.dto

import com.kotlin.aiblogdraft.api.domain.draftImage.dto.DraftImageGroup
import com.kotlin.aiblogdraft.storage.db.entity.DraftEntity
import java.time.LocalDateTime

data class DraftContent(
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
    val imageGroups: List<DraftImageGroup>,
) {
    companion object {
        fun fromDraftEntityAndImageGroups(
            entity: DraftEntity,
            imageGroups: List<DraftImageGroup>,
        ): DraftContent =
            DraftContent(
                title = entity.title,
                content = entity.content!!,
                createdAt = entity.updatedAt,
                imageGroups = imageGroups,
            )
    }
}
