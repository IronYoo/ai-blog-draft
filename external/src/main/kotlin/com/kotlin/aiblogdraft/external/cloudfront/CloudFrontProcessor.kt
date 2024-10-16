package com.kotlin.aiblogdraft.external.cloudfront

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class CloudFrontProcessor(
    @Value("\${image.url}")
    private val imageUrl: String,
) {
    fun generateUrl(imageName: String): String = "$imageUrl/$imageName"
}
