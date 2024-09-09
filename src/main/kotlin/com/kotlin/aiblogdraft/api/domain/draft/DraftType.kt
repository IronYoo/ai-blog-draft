package com.kotlin.aiblogdraft.api.domain.draft

import com.kotlin.aiblogdraft.storage.db.enum.DraftEntityType

enum class DraftType(
    val draftEntityType: DraftEntityType,
) {
    RESTAURANT(DraftEntityType.RESTAURANT),
}
