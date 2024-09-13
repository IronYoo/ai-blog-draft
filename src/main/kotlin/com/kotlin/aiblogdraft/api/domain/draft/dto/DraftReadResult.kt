package com.kotlin.aiblogdraft.api.domain.draft.dto

import com.kotlin.aiblogdraft.storage.db.entity.DraftEntity

data class DraftReadResult(
    val type: DraftType,
    val title: String,
    val regulationText: String? = null,
    val regulationPdfUrl: String? = null,
    val status: DraftStatus,
) {
    companion object {
        fun fromDraftEntity(entity: DraftEntity): DraftReadResult =
            DraftReadResult(
                type = DraftType.findByEntityType(entity.type),
                title = entity.title,
                regulationText = entity.regulationText,
                regulationPdfUrl = entity.regulationPdfUrl,
                status = DraftStatus.findByStatus(entity.status),
            )
    }
}
