package com.kotlin.aiblogdraft.api.domain.draft

import com.kotlin.aiblogdraft.storage.db.enum.DraftEntityStatus

enum class DraftStatus(
    val status: DraftEntityStatus,
) {
    PENDING(DraftEntityStatus.PENDING),
    ;

    companion object {
        fun findByEntityStatus(status: DraftEntityStatus): DraftStatus = DraftStatus.entries.find { it.status == status }!!
    }
}
