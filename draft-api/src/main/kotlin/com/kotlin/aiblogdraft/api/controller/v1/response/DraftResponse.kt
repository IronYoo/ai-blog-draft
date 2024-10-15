package com.kotlin.aiblogdraft.api.controller.v1.response

import com.kotlin.aiblogdraft.api.domain.draft.dto.DraftStatusResult

data class DraftResponse(
    val drafts: List<DraftStatusResult>,
)
