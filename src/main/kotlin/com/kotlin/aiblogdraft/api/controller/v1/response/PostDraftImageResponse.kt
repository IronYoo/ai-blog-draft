package com.kotlin.aiblogdraft.api.controller.v1.response

import com.kotlin.aiblogdraft.api.domain.draftImage.dto.AppendImageResult
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "가초안 이미지 등록 응답")
data class PostDraftImageResponse(
    @Schema(description = "이미지 식별자", example = "1", required = true)
    val id: Long,
    @Schema(description = "이미지 주소", example = "https://google-drive.com", required = true)
    val url: String,
) {
    companion object {
        fun fromAppendImageResult(result: AppendImageResult) =
            PostDraftImageResponse(
                id = result.id,
                url = result.url,
            )
    }
}
