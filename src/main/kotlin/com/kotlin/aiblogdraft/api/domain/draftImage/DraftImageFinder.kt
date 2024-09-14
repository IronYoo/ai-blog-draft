package com.kotlin.aiblogdraft.api.domain.draftImage

import com.kotlin.aiblogdraft.api.domain.draftImage.dto.DraftImage
import com.kotlin.aiblogdraft.api.domain.draftImage.dto.DraftImageGroup
import com.kotlin.aiblogdraft.storage.db.repository.DraftImageGroupRepository
import com.kotlin.aiblogdraft.storage.db.repository.DraftImageRepository
import org.springframework.stereotype.Component

@Component
class DraftImageFinder(
    private val draftImageRepository: DraftImageRepository,
    private val draftImageGroupRepository: DraftImageGroupRepository,
) {
    fun findImageGroups(key: String): List<DraftImageGroup> {
        val groups = draftImageGroupRepository.findOrderedByKey(key)
        return groups.map { group ->
            val images =
                draftImageRepository.findAllByDraftImageGroupId(group.id).map { image ->
                    DraftImage(image.id, image.url)
                }
            DraftImageGroup(group.id, images)
        }
    }
}
