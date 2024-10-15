package com.kotlin.aiblogdraft.external.s3

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

    fun upload(files: Array<MultipartFile>): List<String> {
        val urls =
            files.map {
                val imageName = generateUniqueImageName(it.originalFilename!!)
                val s3Result = s3Template.upload(bucket, imageName, it.inputStream)
                s3Result.url.toString()
            }

        return urls
    }
}
