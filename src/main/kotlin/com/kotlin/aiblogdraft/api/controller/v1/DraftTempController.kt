package com.kotlin.aiblogdraft.api.controller.v1

import com.kotlin.aiblogdraft.api.common.WebUser
import com.kotlin.aiblogdraft.api.config.ApiResponse
import com.kotlin.aiblogdraft.api.controller.v1.response.PostDraftImageResponse
import com.kotlin.aiblogdraft.api.controller.v1.response.StartDraftResponse
import com.kotlin.aiblogdraft.api.domain.DraftImageService
import com.kotlin.aiblogdraft.api.domain.DraftService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/v1/drafts")
class DraftTempController(
    private val draftService: DraftService,
    private val draftImageService: DraftImageService,
) {
    @PostMapping("/temp")
    fun startDraft(webUser: WebUser): ApiResponse<StartDraftResponse> {
        val tempId = draftService.start(webUser.userId)

        return ApiResponse.success(StartDraftResponse(tempId))
    }

    @PostMapping("/images")
    fun postImages(
        @RequestPart(value = "tempId") tempId: String,
        @RequestPart(value = "file") files: Array<MultipartFile>,
        webUser: WebUser,
    ): ApiResponse<List<PostDraftImageResponse>> {
        val appendImageResult = draftImageService.saveImages(tempId.toLong(), files, webUser.userId)
        val response = appendImageResult.map { PostDraftImageResponse.fromAppendImageResult(it) }

        return ApiResponse.success(response)
    }

    @DeleteMapping("/image/{imageId}")
    fun deleteImage(
        @PathVariable(value = "imageId") imageId: Long,
        webUser: WebUser,
    ): ApiResponse<Nothing> {
        draftImageService.delete(imageId, webUser.userId)
        return ApiResponse.success()
    }
}
