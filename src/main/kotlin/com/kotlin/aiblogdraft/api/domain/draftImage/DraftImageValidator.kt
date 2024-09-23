package com.kotlin.aiblogdraft.api.domain.draftImage

import com.kotlin.aiblogdraft.api.exception.DraftImageNotAllowedException
import com.kotlin.aiblogdraft.storage.db.repository.DraftTempRepository
import org.springframework.stereotype.Component

@Component
class DraftImageValidator(
    private val draftTempRepository: DraftTempRepository,
) {
    fun validateUser(
        imageId: Long,
        userId: Long,
    ) {
        val temp = draftTempRepository.findImageUser(imageId)
        if (temp?.userId != userId) throw DraftImageNotAllowedException()
    }
}
