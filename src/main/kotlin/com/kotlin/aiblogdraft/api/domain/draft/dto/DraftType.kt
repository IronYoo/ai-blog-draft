package com.kotlin.aiblogdraft.api.domain.draft.dto

import com.kotlin.aiblogdraft.storage.db.enum.DraftEntityType

enum class DraftType(
    val draftEntityType: DraftEntityType,
    val description: String,
) {
    RESTAURANT(DraftEntityType.RESTAURANT, "맛집"),
    ;

    companion object {
        fun findByEntityType(type: DraftEntityType) = DraftType.entries.find { it.draftEntityType == type }!!
    }
}
