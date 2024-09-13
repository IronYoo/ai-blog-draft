package com.kotlin.aiblogdraft.api.domain.draftKey

import com.kotlin.aiblogdraft.api.domain.draftKey.dto.AppendDraftKey
import com.kotlin.aiblogdraft.storage.db.repository.DraftKeyRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class DraftKeyAppender(
    private val draftKeyRepository: DraftKeyRepository,
) {
    private val log = KotlinLogging.logger {}

    fun appendKey(dto: AppendDraftKey): String {
        val key = UUID.randomUUID().toString()
        log.info { "user(${dto.userId}) append key($key)" }
        return draftKeyRepository.save(dto.toDraftKeyEntity(key)).key
    }
}
