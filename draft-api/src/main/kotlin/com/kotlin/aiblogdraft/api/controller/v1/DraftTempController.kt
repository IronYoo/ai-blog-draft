package com.kotlin.aiblogdraft.api.controller.v1

import com.kotlin.aiblogdraft.api.common.WebUser
import com.kotlin.aiblogdraft.api.config.ApiResponse
import com.kotlin.aiblogdraft.api.controller.v1.response.PostDraftImageResponse
import com.kotlin.aiblogdraft.api.controller.v1.response.StartDraftResponse
import com.kotlin.aiblogdraft.api.domain.DraftImageService
import com.kotlin.aiblogdraft.api.domain.DraftService
import com.kotlin.aiblogdraft.api.domain.DraftTempService
import com.kotlin.aiblogdraft.api.domain.draft.temp.DraftTempFinder
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@Tag(name = "DraftTempController", description = "임시 초안 컨트롤러")
@RequestMapping("/v1/drafts")
class DraftTempController(
    private val draftService: DraftService,
    private val draftImageService: DraftImageService,
    private val draftTempService: DraftTempService,
    private val draftTempFinder: DraftTempFinder,
) {
    @PostMapping("/temp")
    fun startDraft(
        @Parameter(hidden = true) webUser: WebUser,
    ): ApiResponse<StartDraftResponse> {
        val tempId = draftService.start(webUser.userId)

        return ApiResponse.success(StartDraftResponse(tempId))
    }

    @PostMapping("/images")
    fun postImages(
        @RequestPart(value = "tempId") tempId: String,
        @RequestPart(value = "file") files: Array<MultipartFile>,
        @Parameter(hidden = true) webUser: WebUser,
    ): ApiResponse<List<PostDraftImageResponse>> {
        val appendImageResult = draftImageService.saveImages(tempId.toLong(), files, webUser.userId)
        val response = appendImageResult.map { PostDraftImageResponse.fromAppendImageResult(it) }

        return ApiResponse.success(response)
    }

    @DeleteMapping("/image/{imageId}")
    fun deleteImage(
        @PathVariable(value = "imageId") imageId: Long,
        @Parameter(hidden = true) webUser: WebUser,
    ): ApiResponse<Nothing> {
        draftImageService.delete(imageId, webUser.userId)
        return ApiResponse.success()
    }

    @PatchMapping("/{id}/extend")
    fun extend(
        @Parameter(hidden = true) webUser: WebUser,
        @PathVariable(value = "id") id: Long,
    ) {
        draftTempService.extend(id, webUser.userId)
    }
}
