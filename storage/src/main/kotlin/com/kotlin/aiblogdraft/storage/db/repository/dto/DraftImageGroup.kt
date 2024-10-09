package com.kotlin.aiblogdraft.storage.db.repository.dto

import com.kotlin.aiblogdraft.storage.db.entity.DraftImageEntity

class DraftImageGroup(
    val id: Long,
    val images: List<DraftImageEntity>,
    val content: String?,
)
