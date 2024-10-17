package com.kotlin.aiblogdraft.batch.job.draftContent

import com.kotlin.aiblogdraft.batch.domain.image.ImageResizer
import com.kotlin.aiblogdraft.external.cloudfront.CloudFrontProcessor
import com.kotlin.aiblogdraft.external.s3.S3Downloader
import com.kotlin.aiblogdraft.external.s3.S3Uploader
import com.kotlin.aiblogdraft.storage.db.entity.DraftImageEntity
import org.springframework.batch.item.ItemProcessor

class ImageResizeProcessor(
    private val s3Downloader: S3Downloader,
    private val imageResizer: ImageResizer,
    private val s3Uploader: S3Uploader,
    private val cloudFrontProcessor: CloudFrontProcessor,
) : ItemProcessor<DraftImageEntity, DraftImageEntity> {
    private val resizeWidth = 200
    private val resizeHeight = 200

    override fun process(item: DraftImageEntity): DraftImageEntity? {
        val image = s3Downloader.download(item.name)
        val resized = imageResizer.resize(image, resizeWidth, resizeHeight)
        val resizedName = s3Uploader.uploadResized(item.name, resized)

        item.resize(cloudFrontProcessor.generateUrl(resizedName))

        return item
    }
}
