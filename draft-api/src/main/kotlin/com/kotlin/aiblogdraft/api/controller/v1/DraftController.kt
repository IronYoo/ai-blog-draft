package com.kotlin.aiblogdraft.api.controller.v1

import com.kotlin.aiblogdraft.api.common.WebUser
import com.kotlin.aiblogdraft.api.config.ApiResponse
import com.kotlin.aiblogdraft.api.controller.v1.request.CreatePendingDraftRequest
import com.kotlin.aiblogdraft.api.controller.v1.response.DraftResponse
import com.kotlin.aiblogdraft.api.domain.DraftService
import com.kotlin.aiblogdraft.api.domain.draft.dto.Draft
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "DraftController", description = "초안 컨트롤러")
@RestController
@RequestMapping("/v1/drafts")
class DraftController(
    private val draftService: DraftService,
) {
    @PostMapping()
    fun register(
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

    @GetMapping()
    fun get(
        @Parameter(hidden = true) webUser: WebUser,
    ): ApiResponse<DraftResponse> {
        val status = draftService.readAll(webUser.userId)

        return ApiResponse.success(DraftResponse(status))
    }

    @GetMapping("/{id}")
    fun detail(
        @PathVariable(value = "id") id: Long,
        @Parameter(hidden = true) webUser: WebUser,
    ): ApiResponse<Draft> {
        val detail = draftService.readDetail(id, webUser.userId)

        return ApiResponse.success(detail)
    }
}
