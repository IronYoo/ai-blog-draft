package com.kotlin.storage.db.repository.dto

import com.kotlin.storage.db.entity.DraftEntity

data class FindWithRelationsResult(
    val draft: DraftEntity,
    val groups: List<com.kotlin.storage.db.repository.dto.DraftImageGroup>,
)
