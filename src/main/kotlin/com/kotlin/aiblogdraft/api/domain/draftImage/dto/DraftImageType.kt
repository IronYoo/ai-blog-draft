package com.kotlin.aiblogdraft.api.domain.draftImage.dto

import com.kotlin.aiblogdraft.api.exception.DraftImageNotAllowedExtension
import org.springframework.util.MimeType
import org.springframework.util.MimeTypeUtils

enum class DraftImageType(
    val util: MimeType,
) {
    JPG(MimeTypeUtils.IMAGE_JPEG),
    JPEG(MimeTypeUtils.IMAGE_JPEG),
    PNG(MimeTypeUtils.IMAGE_PNG),
    ;

    companion object {
        fun findByLowerCase(type: String) =
            DraftImageType.entries.find { it.name.lowercase() == type.lowercase() } ?: throw DraftImageNotAllowedExtension()
    }
}
