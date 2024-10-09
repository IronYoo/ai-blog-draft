package com.kotlin.aiblogdraft.api.domain.draft.dto

import com.kotlin.aiblogdraft.storage.db.enum.DraftEntityStatus

enum class DraftStatus(
    val status: DraftEntityStatus,
) {
    PENDING(DraftEntityStatus.PENDING),
    PROCESSING(DraftEntityStatus.PROCESSING),
    DONE(DraftEntityStatus.DONE),
    ;

    companion object {
        fun findByStatus(status: DraftEntityStatus) = DraftStatus.entries.find { it.status == status }!!

        fun isDone(status: DraftEntityStatus) = status == DONE.status
    }
}
