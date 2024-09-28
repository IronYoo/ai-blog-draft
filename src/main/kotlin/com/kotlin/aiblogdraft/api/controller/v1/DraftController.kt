package com.kotlin.aiblogdraft.api.controller.v1

import com.kotlin.aiblogdraft.api.common.WebUser
import com.kotlin.aiblogdraft.api.config.ApiResponse
import com.kotlin.aiblogdraft.api.controller.v1.request.CreatePendingDraftRequest
import com.kotlin.aiblogdraft.api.controller.v1.response.PostDraftImageResponse
import com.kotlin.aiblogdraft.api.controller.v1.response.StartDraftResponse
import com.kotlin.aiblogdraft.api.domain.DraftImageService
import com.kotlin.aiblogdraft.api.domain.DraftService
import com.kotlin.aiblogdraft.api.domain.draft.dto.Draft
import com.kotlin.aiblogdraft.api.domain.draft.dto.DraftStatusResult
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/v1/drafts")
class DraftController(
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

    @PostMapping()
    fun pendDraft(
        @Valid @RequestBody body: CreatePendingDraftRequest,
        webUser: WebUser,
    ): ApiResponse<Nothing> {
        draftService.append(
            userId = webUser.userId,
            tempId = body.tempId,
            appendDraft = body.toAppendDraft(),
        )

        return ApiResponse.success()
    }

    @GetMapping("/status")
    fun status(webUser: WebUser): ApiResponse<List<DraftStatusResult>> {
        val status = draftService.status(webUser.userId)

        return ApiResponse.success(status)
    }

    @GetMapping("/{id}")
    fun detail(
        @PathVariable(value = "id") id: Long,
        webUser: WebUser,
    ): ApiResponse<Draft> {
        val draftWithImageGroups = draftService.read(id, webUser.userId)

        return ApiResponse.success(draftWithImageGroups)
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
