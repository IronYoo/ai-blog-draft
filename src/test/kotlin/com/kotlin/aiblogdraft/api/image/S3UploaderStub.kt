package com.kotlin.aiblogdraft.api.image

import com.kotlin.aiblogdraft.image.S3Uploader
import io.mockk.mockk
import org.springframework.web.multipart.MultipartFile

class S3UploaderStub : S3Uploader(mockk()) {
    override fun upload(files: Array<MultipartFile>): List<String> = files.mapIndexed { index, _ -> "http://$index" }
}
