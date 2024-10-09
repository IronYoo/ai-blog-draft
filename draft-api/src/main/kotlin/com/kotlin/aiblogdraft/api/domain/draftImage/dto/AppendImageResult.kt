package com.kotlin.aiblogdraft.api.domain.draftImage.dto

import com.kotlin.aiblogdraft.storage.db.entity.DraftImageEntity

data class AppendImageResult(
    val id: Long,
    val url: String,
) {
    companion object {
        fun fromImageEntity(entity: DraftImageEntity) =
            AppendImageResult(
                id = entity.id,
                url = entity.url,
            )
    }
}
