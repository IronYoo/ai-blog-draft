package com.kotlin.aiblogdraft.api.controller.v1.request

import com.kotlin.aiblogdraft.api.domain.draft.dto.AppendDraft
import com.kotlin.aiblogdraft.api.domain.draft.dto.DraftType
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length

@Schema(description = "대기 상태 초안 요청")
data class CreatePendingDraftRequest(
    @Schema(description = "가초안 식별자", example = "1", required = true)
    val tempId: Long,
    @Schema(description = "초안 종류", example = "RESTAURANT", required = true)
    val type: DraftType,
    @field:NotBlank
    @field:Length(min = 2, max = 255, message = "제목은 2자 이상, 255자 이하이어야 합니다.")
    @Schema(description = "초안 제목", example = "강남 맛집", required = true)
    val title: String,
) {
    fun toAppendDraft() =
        AppendDraft(
            type = type,
            title = title,
        )
}
