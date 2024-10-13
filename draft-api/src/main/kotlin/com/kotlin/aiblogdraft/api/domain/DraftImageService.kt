package com.kotlin.aiblogdraft.api.domain

import com.kotlin.aiblogdraft.api.domain.draft.image.DraftImageRemover
import com.kotlin.aiblogdraft.api.domain.draft.image.DraftImageSaver
import com.kotlin.aiblogdraft.api.domain.draft.image.dto.AppendImageResult
import com.kotlin.aiblogdraft.api.domain.draft.image.dto.DraftImageDeleteEvent
import com.kotlin.aiblogdraft.api.domain.draft.temp.DraftTempFinder
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class DraftImageService(
    private val draftTempFinder: DraftTempFinder,
    private val draftImageSaver: DraftImageSaver,
    private val draftImageRemover: DraftImageRemover,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    fun saveImages(
        tempId: Long,
        files: Array<MultipartFile>,
        userId: Long,
    ): List<AppendImageResult> {
        val draftTempId = draftTempFinder.getValid(tempId, userId).id
        val images = draftImageSaver.save(draftTempId, files)

        return images.map { AppendImageResult.fromImageEntity(it) }
    }

    fun delete(
        imageId: Long,
        userId: Long,
    ) {
        val image = draftImageRemover.remove(imageId, userId)
        applicationEventPublisher.publishEvent(DraftImageDeleteEvent(mutableListOf(image.url)))
    }
}
