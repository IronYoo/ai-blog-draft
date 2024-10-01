package com.kotlin.aiblogdraft.api.controller.v1

import com.kotlin.aiblogdraft.api.common.WebUser
import com.kotlin.aiblogdraft.api.config.ApiResponse
import com.kotlin.aiblogdraft.api.controller.v1.request.CreatePendingDraftRequest
import com.kotlin.aiblogdraft.api.domain.DraftService
import com.kotlin.aiblogdraft.api.domain.draft.dto.Draft
import com.kotlin.aiblogdraft.api.domain.draft.dto.DraftStatusResult
import io.swagger.v3.oas.annotations.Parameter
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/drafts")
class DraftController(
    private val draftService: DraftService,
) {
    @PostMapping()
    fun pendDraft(
        @Valid @RequestBody body: CreatePendingDraftRequest,
        @Parameter(hidden = true) webUser: WebUser,
    ): ApiResponse<Nothing> {
        draftService.append(
            userId = webUser.userId,
            tempId = body.tempId,
            appendDraft = body.toAppendDraft(),
        )

        return ApiResponse.success()
    }

    @GetMapping("/status")
    fun status(
        @Parameter(hidden = true) webUser: WebUser,
    ): ApiResponse<List<DraftStatusResult>> {
        val status = draftService.status(webUser.userId)

        return ApiResponse.success(status)
    }

    @GetMapping("/{id}")
    fun detail(
        @PathVariable(value = "id") id: Long,
        @Parameter(hidden = true) webUser: WebUser,
    ): ApiResponse<Draft> {
        val draftWithImageGroups = draftService.read(id, webUser.userId)

        return ApiResponse.success(draftWithImageGroups)
    }
}
