package com.kotlin.aiblogdraft.api.controller.v1

import com.kotlin.aiblogdraft.api.config.ApiResponse
import com.kotlin.aiblogdraft.api.controller.v1.request.CreatePendingDraftRequest
import com.kotlin.aiblogdraft.api.controller.v1.request.StartDraftRequest
import com.kotlin.aiblogdraft.api.controller.v1.response.PostDraftImageResponse
import com.kotlin.aiblogdraft.api.controller.v1.response.StartDraftResponse
import com.kotlin.aiblogdraft.api.domain.DraftImageService
import com.kotlin.aiblogdraft.api.domain.DraftService
import com.kotlin.aiblogdraft.api.domain.draft.dto.Draft
import com.kotlin.aiblogdraft.api.domain.draft.dto.DraftStatusResult
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/v1/drafts")
class DraftController(
    private val draftService: DraftService,
    private val draftImageService: DraftImageService,
) {
    private val log = KotlinLogging.logger {}

    @PostMapping("/temp")
    fun startDraft(
        @RequestBody body: StartDraftRequest,
    ): ApiResponse<StartDraftResponse> {
        val tempId = draftService.start(body.userId)

        return ApiResponse.success(StartDraftResponse(tempId))
    }

    @PostMapping("/images")
    fun postImages(
        @RequestPart(value = "tempId") tempId: String,
        @RequestPart(value = "file") files: Array<MultipartFile>,
        @RequestPart(value = "userId") userId: String,
    ): ApiResponse<List<PostDraftImageResponse>> {
        val appendImageResult = draftImageService.saveImages(tempId.toLong(), files, userId.toLong())
        val response = appendImageResult.map { PostDraftImageResponse.fromAppendImageResult(it) }

        return ApiResponse.success(response)
    }

    @PostMapping()
    fun pendDraft(
        @Valid @RequestBody body: CreatePendingDraftRequest,
    ): ApiResponse<Nothing> {
        draftService.append(
            userId = body.userId,
            tempId = body.tempId,
            appendDraft = body.toAppendDraft(),
        )

        return ApiResponse.success()
    }

    @GetMapping("/status")
    fun status(
        @RequestParam(value = "userId") userId: Long,
    ): ApiResponse<List<DraftStatusResult>> {
        val status = draftService.status(userId)

        return ApiResponse.success(status)
    }

    @GetMapping("/{id}")
    fun detail(
        @PathVariable(value = "id") id: Long,
        @RequestParam(value = "userId") userId: Long,
    ): ApiResponse<Draft> {
        val draftWithImageGroups = draftService.read(id, userId)

        return ApiResponse.success(draftWithImageGroups)
    }

    @DeleteMapping("/image/{imageId}")
    fun deleteImage(
        @PathVariable(value = "imageId") imageId: Long,
        @RequestParam(value = "userId") userId: Long,
    ): ApiResponse<Nothing> {
        draftImageService.delete(imageId, userId)
        return ApiResponse.success()
    }
}
