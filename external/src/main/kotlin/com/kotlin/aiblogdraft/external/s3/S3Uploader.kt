package com.kotlin.aiblogdraft.external.s3

import com.kotlin.aiblogdraft.external.s3.dto.S3UploadResult
import io.awspring.cloud.s3.S3Template
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Component
class S3Uploader(
    private val s3Template: S3Template,
) {
    @Value("\${spring.cloud.aws.s3.bucket}")
    private lateinit var bucket: String

    private fun generateUniqueImageName(originName: String): String =
        UUID.randomUUID().toString() + "." + StringUtils.getFilenameExtension(originName)

    fun upload(files: Array<MultipartFile>): List<S3UploadResult> {
        val result =
            files.map {
                val imageName = generateUniqueImageName(it.originalFilename!!)
                val s3Result = s3Template.upload(bucket, imageName, it.inputStream)
                S3UploadResult(imageName, s3Result.url.toString())
            }

        return result
    }
}
