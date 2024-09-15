package com.kotlin.aiblogdraft.api.domain.draftImage

import com.kotlin.aiblogdraft.image.S3Uploader
import com.kotlin.aiblogdraft.storage.db.TransactionHandler
import com.kotlin.aiblogdraft.storage.db.entity.DraftImageEntity
import com.kotlin.aiblogdraft.storage.db.entity.DraftImageGroupEntity
import com.kotlin.aiblogdraft.storage.db.repository.DraftImageGroupRepository
import com.kotlin.aiblogdraft.storage.db.repository.DraftImageRepository
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class DraftImageSaver(
    private val draftImageGroupRepository: DraftImageGroupRepository,
    private val draftImageRepository: DraftImageRepository,
    private val s3Uploader: S3Uploader,
    private val transactionHandler: TransactionHandler,
) {
    private fun store(
        draftKey: String,
        urls: List<String>,
    ): List<DraftImageEntity> {
        val images =
            transactionHandler.executeTransaction {
                val groupId = draftImageGroupRepository.save(DraftImageGroupEntity(draftKey)).id
                val imageEntities = urls.map { url -> DraftImageEntity(url, groupId) }
                draftImageRepository.saveAll(imageEntities)
            }

        return images
    }

    fun save(
        draftKey: String,
        files: Array<MultipartFile>,
    ): List<DraftImageEntity> {
        val urls = s3Uploader.upload(files)

        return store(draftKey, urls)
    }
}
