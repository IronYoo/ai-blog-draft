package com.kotlin.aiblogdraft.api.domain.draft.dto

import com.kotlin.aiblogdraft.storage.db.enum.AiPromptEntityType
import com.kotlin.aiblogdraft.storage.db.enum.DraftEntityType

enum class DraftType(
    val draftEntityType: com.kotlin.aiblogdraft.storage.db.enum.DraftEntityType,
    val description: String,
    val aiPromptEntityType: AiPromptEntityType,
) {
    RESTAURANT(com.kotlin.aiblogdraft.storage.db.enum.DraftEntityType.RESTAURANT, "맛집", AiPromptEntityType.DRAFT_RESTAURANT),
    ;

    companion object {
        fun findByEntityType(type: com.kotlin.aiblogdraft.storage.db.enum.DraftEntityType) =
            DraftType.entries.find {
                it.draftEntityType ==
                    type
            }!!
    }
}
