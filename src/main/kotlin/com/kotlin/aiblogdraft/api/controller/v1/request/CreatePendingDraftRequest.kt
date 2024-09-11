package com.kotlin.aiblogdraft.api.controller.v1.request

import com.kotlin.aiblogdraft.api.domain.draft.AppendDraft
import com.kotlin.aiblogdraft.api.domain.draft.DraftType

data class CreatePendingDraftRequest(
    val userId: Long,
    val key: String,
    val type: DraftType,
    val title: String,
)

fun CreatePendingDraftRequest.toAppendDraft() =
    AppendDraft(
        key = key,
        type = type,
        title = title,
        userId = userId,
    )
