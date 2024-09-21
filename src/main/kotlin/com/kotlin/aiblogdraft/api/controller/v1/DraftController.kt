package com.kotlin.aiblogdraft.api.controller.v1

import com.kotlin.aiblogdraft.api.config.ApiResponse
import com.kotlin.aiblogdraft.api.controller.v1.request.CreateDraftKeyRequest
import com.kotlin.aiblogdraft.api.controller.v1.request.CreatePendingDraftRequest
import com.kotlin.aiblogdraft.api.controller.v1.response.PostDraftImageResponse
import com.kotlin.aiblogdraft.api.domain.DraftService
import com.kotlin.aiblogdraft.api.domain.draft.dto.Draft
import com.kotlin.aiblogdraft.api.domain.draft.dto.DraftStatusResult
import com.kotlin.aiblogdraft.image.S3Uploader
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.Valid
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
    private val s3Uploader: S3Uploader,
) {
    private val log = KotlinLogging.logger {}

    @PostMapping("/key")
    fun createDraftKey(
        @RequestBody body: CreateDraftKeyRequest,
    ): ApiResponse<String> {
        val userId = body.userId
        val key = draftService.createKey(userId)
        log.info { "user(${body.userId}) issued draftKey($key)" }

        return ApiResponse.success(key)
    }

    @PostMapping("/images")
    fun postDraftImages(
        @RequestPart(value = "key") key: String,
        @RequestPart(value = "file") files: Array<MultipartFile>,
        @RequestPart(value = "userId") userId: String,
    ): ApiResponse<List<PostDraftImageResponse>> {
        val appendImageResult = draftService.saveImages(key, files, userId.toLong())
        val response = appendImageResult.map { PostDraftImageResponse.fromAppendImageResult(it) }

        return ApiResponse.success(response)
    }

    @PostMapping()
    fun createPendingDraft(
        @Valid @RequestBody body: CreatePendingDraftRequest,
    ): ApiResponse<Long> {
        val id = draftService.append(body.toAppendDraft())

        return ApiResponse.success(id)
    }

    @GetMapping("/status")
    fun getStatus(
        @RequestParam(value = "userId") userId: Long,
    ): ApiResponse<List<DraftStatusResult>> {
        val status = draftService.status(userId)

        return ApiResponse.success(status)
    }

    @GetMapping("/{id}")
    fun get(
        @PathVariable(value = "id") id: Long,
        @RequestParam(value = "userId") userId: Long,
    ): ApiResponse<Draft> {
        val draftWithImageGroups = draftService.read(id, userId)

        return ApiResponse.success(draftWithImageGroups)
    }
}
