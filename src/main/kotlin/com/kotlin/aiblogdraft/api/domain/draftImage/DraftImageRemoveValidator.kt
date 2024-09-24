package com.kotlin.aiblogdraft.api.domain.draftImage

import com.kotlin.aiblogdraft.api.exception.DraftImageModificationDeniedException
import com.kotlin.aiblogdraft.api.exception.DraftImageNotAllowedException
import com.kotlin.aiblogdraft.storage.db.repository.DraftRepository
import com.kotlin.aiblogdraft.storage.db.repository.DraftTempRepository
import org.springframework.stereotype.Component

@Component
class DraftImageRemoveValidator(
    private val draftRepository: DraftRepository,
    private val draftTempRepository: DraftTempRepository,
) {
    fun validate(
        imageId: Long,
        userId: Long,
    ) {
        draftRepository.findByDraftImage(imageId)?.let {
            throw DraftImageModificationDeniedException()
        }
        val temp = draftTempRepository.findImageUser(imageId)
        if (temp?.userId != userId) throw DraftImageNotAllowedException()
    }
}
