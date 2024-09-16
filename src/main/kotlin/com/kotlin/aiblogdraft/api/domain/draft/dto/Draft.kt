package com.kotlin.aiblogdraft.api.domain.draft.dto

import com.kotlin.aiblogdraft.storage.db.repository.dto.DraftWithImageGroups

data class Draft(
    val id: Long,
    val type: DraftType,
    val title: String,
    val regulationText: String?,
    val regulationPdfUrl: String?,
    val status: DraftStatus,
    val content: String?,
    val groups: List<DraftImageGroup>,
) {
    companion object {
        fun fromDraftWithImageGroups(dto: DraftWithImageGroups) =
            Draft(
                id = dto.draft.id,
                type = DraftType.findByEntityType(dto.draft.type),
                title = dto.draft.title,
                regulationText = dto.draft.regulationText,
                regulationPdfUrl = dto.draft.regulationPdfUrl,
                status = DraftStatus.findByStatus(dto.draft.status),
                content = dto.draft.content,
                groups =
                    dto.groups.map { group ->
                        val images = group.images.map { DraftImage(it.id, it.url) }
                        DraftImageGroup(group.id, images)
                    },
            )
    }
}
