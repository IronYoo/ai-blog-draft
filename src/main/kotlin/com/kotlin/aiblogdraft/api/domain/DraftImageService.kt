package com.kotlin.aiblogdraft.api.domain

import com.kotlin.aiblogdraft.api.domain.draftImage.DraftImageSaver
import com.kotlin.aiblogdraft.api.domain.draftImage.DraftTempImageRemover
import com.kotlin.aiblogdraft.api.domain.draftImage.dto.AppendImageResult
import com.kotlin.aiblogdraft.api.domain.draftTemp.DraftTempFinder
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class DraftImageService(
    private val draftTempFinder: DraftTempFinder,
    private val draftImageSaver: DraftImageSaver,
    private val draftTempImageRemover: DraftTempImageRemover,
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

    fun deleteTemp(
        imageId: Long,
        userId: Long,
    ) {
        draftTempImageRemover.remove(imageId, userId)
    }
}
