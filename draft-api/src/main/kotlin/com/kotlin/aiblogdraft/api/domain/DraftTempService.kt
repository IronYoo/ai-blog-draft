package com.kotlin.aiblogdraft.api.domain

import com.kotlin.aiblogdraft.api.domain.draftTemp.DraftTempModifier
import org.springframework.stereotype.Service

@Service
class DraftTempService(
    private val draftTempModifier: DraftTempModifier,
) {
    fun extend(
        userId: Long,
        tempId: Long,
    ) {
        draftTempModifier.postponeRemove(userId, tempId)
    }
}
