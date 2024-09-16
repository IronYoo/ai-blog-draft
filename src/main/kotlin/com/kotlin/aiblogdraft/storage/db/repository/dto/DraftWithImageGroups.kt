package com.kotlin.aiblogdraft.storage.db.repository.dto

import com.kotlin.aiblogdraft.storage.db.entity.DraftEntity

data class DraftWithImageGroups(
    val draft: DraftEntity,
    val groups: List<DraftImageGroup>,
)
