package com.kotlin.aiblogdraft.api.domain.draft.dto

import com.kotlin.aiblogdraft.storage.db.repository.dto.FindWithRelationsResult

data class Draft(
    val id: Long,
    val type: DraftType,
    val title: String,
    val regulationText: String?,
    val status: DraftStatus,
    val imageGroups: List<DraftImageGroup>,
) {
    companion object {
        fun fromDetail(dto: FindWithRelationsResult) =
            Draft(
                id = dto.draft.id,
                type = DraftType.findByEntityType(dto.draft.type),
                title = dto.draft.title,
                regulationText = dto.draft.regulationText,
                status = DraftStatus.findByStatus(dto.draft.status),
                imageGroups =
                    dto.groups.map { group ->
                        val images = group.images.map { DraftImage(it.id, it.url) }
                        DraftImageGroup(group.id, images, group.content!!)
                    },
            )
    }
}
