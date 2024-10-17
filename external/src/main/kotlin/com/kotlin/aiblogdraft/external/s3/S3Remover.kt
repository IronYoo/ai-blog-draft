package com.kotlin.aiblogdraft.external.s3

import io.awspring.cloud.s3.S3Template
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class S3Remover(
    private val s3Template: S3Template,
) {
    @Value("\${spring.cloud.aws.s3.bucket}")
    private lateinit var bucket: String

    fun remove(key: String) {
        s3Template.deleteObject(bucket, key)
    }
}
