package com.kotlin.aiblogdraft.api.domain.draftTemp.dto

import com.kotlin.storage.db.entity.DraftTempEntity

data class AppendDraftTemp(
    val userId: Long,
) {
    fun toDraftTempEntity() =
        DraftTempEntity(
            userId = userId,
        )
}
