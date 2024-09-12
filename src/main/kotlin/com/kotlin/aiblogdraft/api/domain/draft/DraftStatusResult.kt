package com.kotlin.aiblogdraft.api.domain.draft

data class DraftStatusResult(
    val id: Long,
    val title: String,
    val status: DraftStatus,
)
