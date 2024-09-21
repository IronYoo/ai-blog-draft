package com.kotlin.aiblogdraft.api.controller.v1.request

import com.kotlin.aiblogdraft.api.domain.draft.dto.AppendDraft
import com.kotlin.aiblogdraft.api.domain.draft.dto.DraftType
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length

data class CreatePendingDraftRequest(
    val userId: Long,
    val key: String,
    val type: DraftType,
    @field:NotBlank
    @field:Length(min = 2, max = 255, message = "제목은 2자 이상, 255자 이하이어야 합니다.")
    val title: String,
) {
    fun toAppendDraft() =
        AppendDraft(
            key = key,
            type = type,
            title = title,
            userId = userId,
        )
}
