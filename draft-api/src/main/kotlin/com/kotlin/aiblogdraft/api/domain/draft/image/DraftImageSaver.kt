package com.kotlin.aiblogdraft.api.domain.draft.image

import com.kotlin.aiblogdraft.api.domain.draft.image.dto.DraftImageType
import com.kotlin.aiblogdraft.external.s3.S3Uploader
import com.kotlin.aiblogdraft.storage.db.TransactionHandler
import com.kotlin.aiblogdraft.storage.db.entity.DraftImageEntity
import com.kotlin.aiblogdraft.storage.db.entity.DraftImageGroupEntity
import com.kotlin.aiblogdraft.storage.db.repository.DraftImageGroupRepository
import com.kotlin.aiblogdraft.storage.db.repository.DraftImageRepository
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.net.URL

@Component
class DraftImageSaver(
    private val draftImageGroupRepository: DraftImageGroupRepository,
    private val draftImageRepository: DraftImageRepository,
    private val s3Uploader: S3Uploader,
    private val transactionHandler: TransactionHandler,
) {
    private fun imageType(imageUrl: String): DraftImageType {
        val url = URL(imageUrl)
        val fileName = url.path.substringAfterLast('/')
        val extension = fileName.substringAfterLast('.', missingDelimiterValue = "")

        return DraftImageType.findByLowerCase(extension)
    }

    private fun store(
        tempId: Long,
        urls: List<String>,
    ): List<DraftImageEntity> {
        val images =
            transactionHandler.executeTransaction {
                val group = draftImageGroupRepository.save(DraftImageGroupEntity(tempId))
                val imageEntities = urls.map { url -> DraftImageEntity(url, group.id, imageType(url).util) }
                draftImageRepository.saveAll(imageEntities)
            }

        return images
    }

    fun save(
        tempId: Long,
        files: Array<MultipartFile>,
    ): List<DraftImageEntity> {
        val urls = s3Uploader.upload(files)

        return store(tempId, urls)
    }
}
