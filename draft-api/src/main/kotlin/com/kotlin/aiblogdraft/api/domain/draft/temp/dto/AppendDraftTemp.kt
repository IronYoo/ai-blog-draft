package com.kotlin.aiblogdraft.api.domain.draft.temp.dto

import com.kotlin.aiblogdraft.storage.db.entity.DraftTempEntity

data class AppendDraftTemp(
    val userId: Long,
) {
    fun toDraftTempEntity() =
        DraftTempEntity(
            userId = userId,
        )
}
