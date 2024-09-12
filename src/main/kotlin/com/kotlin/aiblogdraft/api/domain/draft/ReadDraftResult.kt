package com.kotlin.aiblogdraft.api.domain.draft

import com.kotlin.aiblogdraft.storage.db.entity.DraftEntity

data class ReadDraftResult(
    val type: DraftType,
    val title: String,
    val regulationText: String? = null,
    val regulationPdfUrl: String? = null,
    val status: DraftStatus,
) {
    companion object {
        fun fromDraftEntity(entity: DraftEntity): ReadDraftResult =
            ReadDraftResult(
                type = DraftType.findByEntityType(entity.type),
                title = entity.title,
                regulationText = entity.regulationText,
                regulationPdfUrl = entity.regulationPdfUrl,
                status = DraftStatus.findByEntityStatus(entity.status),
            )
    }
}
