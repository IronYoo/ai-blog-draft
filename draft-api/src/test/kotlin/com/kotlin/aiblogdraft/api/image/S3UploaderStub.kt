package com.kotlin.aiblogdraft.api.image

import com.kotlin.aiblogdraft.external.s3.S3Uploader
import com.kotlin.aiblogdraft.external.s3.dto.S3UploadResult
import io.mockk.mockk
import org.springframework.web.multipart.MultipartFile

class S3UploaderStub : S3Uploader(mockk()) {
    override fun upload(files: Array<MultipartFile>): List<S3UploadResult> =
        files.mapIndexed { index, _ ->
            S3UploadResult(
                url = "https://test-image.mochayoo.site/image_$index.jpeg",
                name = "image_$index.jpeg",
            )
        }
}
