package com.kotlin.aiblogdraft.api.controller.v1

import com.kotlin.aiblogdraft.api.controller.v1.request.CreateDraftKeyRequest
import com.kotlin.aiblogdraft.api.controller.v1.request.CreatePendingDraftRequest
import com.kotlin.aiblogdraft.api.controller.v1.response.PostDraftImageResponse
import com.kotlin.aiblogdraft.api.domain.DraftService
import com.kotlin.aiblogdraft.image.S3Uploader
import io.github.oshai.kotlinlogging.KotlinLogging
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
    ): String {
        val userId = body.userId
        val key = draftService.createKey(userId)
        log.info { "user(${body.userId}) issued draftKey($key)" }
        return key
    }

    @PostMapping("/images")
    fun postDraftImages(
        @RequestPart(value = "key") key: String,
        @RequestPart(value = "file") files: Array<MultipartFile>,
        @RequestPart(value = "userId") userId: String,
    ): List<PostDraftImageResponse> {
        val appendImageResult = draftService.saveImages(key, files, userId.toLong())
        return appendImageResult.map { PostDraftImageResponse.fromAppendImageResult(it) }
    }

    @PostMapping()
    fun createPendingDraft(
        @RequestBody body: CreatePendingDraftRequest,
    ) = draftService.append(body.toAppendDraft())

    @GetMapping("/status")
    fun getStatus(
        @RequestParam(value = "userId") userId: Long,
    ) = draftService.status(userId)

    @GetMapping("/{id}")
    fun get(
        @PathVariable(value = "id") id: Long,
    ) = draftService.read(id)

    @GetMapping("/{id}/content")
    fun getContent(
        @PathVariable(value = "id") id: Long,
    ) = draftService.content(id)
}
