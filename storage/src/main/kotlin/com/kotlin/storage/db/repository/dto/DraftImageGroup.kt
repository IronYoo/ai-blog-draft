package com.kotlin.storage.db.repository.dto

import com.kotlin.storage.db.entity.DraftImageEntity

class DraftImageGroup(
    val id: Long,
    val images: List<DraftImageEntity>,
    val content: String?,
)
