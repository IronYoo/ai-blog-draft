package com.kotlin.aiblogdraft.api.domain.draftTemp

import com.kotlin.aiblogdraft.storage.db.repository.DraftTempRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class DraftTempModifier(
    private val draftTempFinder: DraftTempFinder,
    private val draftTempRepository: DraftTempRepository,
) {
    private val delayDays = 7L

    fun postponeRemove(
        userId: Long,
        tempId: Long,
    ) {
        draftTempFinder.getValid(tempId, userId)

        val removeAt =
            LocalDateTime
                .now()
                .plusDays(delayDays)
                .toLocalDate()
                .atStartOfDay()
                .plusDays(1)
        draftTempRepository.updateExpireAt(tempId, removeAt)
    }
}
