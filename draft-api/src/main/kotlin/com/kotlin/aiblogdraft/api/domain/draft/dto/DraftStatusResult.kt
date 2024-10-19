package com.kotlin.aiblogdraft.api.domain.draft.dto

import java.time.LocalDateTime

data class DraftStatusResult(
    val id: Long,
    val title: String,
    val status: DraftStatus,
    val registeredAt: LocalDateTime,
)
