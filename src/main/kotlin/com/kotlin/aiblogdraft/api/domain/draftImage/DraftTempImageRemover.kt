package com.kotlin.aiblogdraft.api.domain.draftImage

import com.kotlin.aiblogdraft.storage.db.repository.DraftImageGroupRepository
import com.kotlin.aiblogdraft.storage.db.repository.DraftImageRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DraftTempImageRemover(
    private val draftImageReader: DraftImageReader,
    private val draftImageValidator: DraftImageValidator,
    private val draftImageGroupRepository: DraftImageGroupRepository,
    private val draftImageRepository: DraftImageRepository,
) {
    @Transactional
    fun remove(
        imageId: Long,
        userId: Long,
    ) {
        draftImageValidator.validateUser(imageId, userId)

        val image = draftImageReader.read(imageId)
        draftImageRepository.delete(image)

        if (draftImageRepository.countByDraftImageGroupId(image.draftImageGroupId) == 0) {
            draftImageGroupRepository.deleteById(image.draftImageGroupId)
        }
    }
}
