package com.kotlin.aiblogdraft.api.domain

import com.kotlin.aiblogdraft.storage.db.enum.DraftEntityType

enum class DraftType(
    val draftEntityType: DraftEntityType,
) {
    RESTAURANT(DraftEntityType.RESTAURANT),
}
