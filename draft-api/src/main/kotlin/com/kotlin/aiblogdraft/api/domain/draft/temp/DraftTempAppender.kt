package com.kotlin.aiblogdraft.api.domain.draft.temp

import com.kotlin.aiblogdraft.api.domain.draft.temp.dto.AppendDraftTemp
import com.kotlin.aiblogdraft.storage.db.repository.DraftTempRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component

@Component
class DraftTempAppender(
    private val draftTempRepository: DraftTempRepository,
) {
    private val log = KotlinLogging.logger {}

    fun append(dto: AppendDraftTemp): Long {
        val tempId = draftTempRepository.save(dto.toDraftTempEntity()).id
        log.info { "user(${dto.userId}) append temp($tempId)" }

        return tempId
    }
}
