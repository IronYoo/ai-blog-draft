package com.kotlin.aiblogdraft.storage.db.repository.dto

import com.kotlin.aiblogdraft.storage.db.entity.DraftEntity

data class FindWithRelationsResult(
    val draft: DraftEntity,
    val groups: List<com.kotlin.aiblogdraft.storage.db.repository.dto.DraftImageGroup>,
)
