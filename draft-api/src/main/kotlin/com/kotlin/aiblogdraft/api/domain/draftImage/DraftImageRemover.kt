package com.kotlin.aiblogdraft.api.domain.draftImage

import com.kotlin.aiblogdraft.api.domain.draftImage.dto.DraftImageDeleteEvent
import com.kotlin.aiblogdraft.image.S3Remover
import com.kotlin.storage.db.entity.DraftImageEntity
import com.kotlin.storage.db.repository.DraftImageGroupRepository
import com.kotlin.storage.db.repository.DraftImageRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DraftImageRemover(
    private val draftImageReader: DraftImageReader,
    private val draftImageRemoveValidator: DraftImageRemoveValidator,
    private val draftImageGroupRepository: DraftImageGroupRepository,
    private val draftImageRepository: DraftImageRepository,
    private val s3Remover: S3Remover,
) {
    private val log = KotlinLogging.logger {}

    @Transactional
    fun remove(
        imageId: Long,
        userId: Long,
    ): DraftImageEntity {
        draftImageRemoveValidator.validate(imageId, userId)

        val image = draftImageReader.read(imageId)
        draftImageRepository.delete(image)

        if (draftImageRepository.countByDraftImageGroupId(image.draftImageGroupId) == 0) {
            draftImageGroupRepository.deleteById(image.draftImageGroupId)
        }

        return image
    }

    @Async
    @EventListener
    fun removeEventHandle(event: DraftImageDeleteEvent) {
        event.urls.forEach {
            log.info { "s3 remove ($it)" }
            s3Remover.remove(it)
        }
    }
}
