package com.kotlin.aiblogdraft.image

import io.awspring.cloud.s3.S3Template
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class S3Remover(
    private val s3Template: S3Template,
) {
    @Value("\${spring.cloud.aws.s3.bucket}")
    private lateinit var bucket: String

    private fun extractFileNameFromUrl(url: String): String = url.substringAfterLast("/")

    fun remove(url: String) {
        val key = extractFileNameFromUrl(url)
        s3Template.deleteObject(bucket, key)
    }
}
