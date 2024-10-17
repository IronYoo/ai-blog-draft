package com.kotlin.aiblogdraft.batch.domain.image

import net.coobird.thumbnailator.Thumbnails
import org.springframework.stereotype.Component
import java.io.ByteArrayOutputStream
import java.io.InputStream

@Component
class ImageResizer {
    fun resize(
        inputStream: InputStream,
        width: Int,
        height: Int,
    ): ByteArray {
        val outputStream = ByteArrayOutputStream()
        Thumbnails
            .of(inputStream)
            .size(width, height)
            .toOutputStream(outputStream)
        return outputStream.toByteArray()
    }
}
