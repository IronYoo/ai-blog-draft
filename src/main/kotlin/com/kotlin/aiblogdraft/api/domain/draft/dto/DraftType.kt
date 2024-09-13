package com.kotlin.aiblogdraft.api.domain.draft.dto

import com.kotlin.aiblogdraft.storage.db.enum.DraftEntityType

enum class DraftType(
    val draftEntityType: DraftEntityType,
) {
    RESTAURANT(DraftEntityType.RESTAURANT),
    ;

    companion object {
        fun findByEntityType(type: DraftEntityType) = DraftType.entries.find { it.draftEntityType == type }!!
    }
}
