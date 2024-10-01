package com.kotlin.aiblogdraft.api.controller.v1.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "가초안 생성 응답")
class StartDraftResponse(
    @Schema(description = "가초안 식별자", example = "1", required = true)
    val tempId: Long,
)
