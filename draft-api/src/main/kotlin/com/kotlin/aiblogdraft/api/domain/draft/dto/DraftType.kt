package com.kotlin.aiblogdraft.api.domain.draft.dto

import com.kotlin.storage.db.enum.AiPromptEntityType
import com.kotlin.storage.db.enum.DraftEntityType

enum class DraftType(
    val draftEntityType: DraftEntityType,
    val description: String,
    val aiPromptEntityType: AiPromptEntityType,
) {
    RESTAURANT(DraftEntityType.RESTAURANT, "맛집", AiPromptEntityType.DRAFT_RESTAURANT),
    ;

    companion object {
        fun findByEntityType(type: DraftEntityType) = DraftType.entries.find { it.draftEntityType == type }!!
    }
}
