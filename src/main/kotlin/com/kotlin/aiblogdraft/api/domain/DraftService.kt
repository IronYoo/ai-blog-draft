package com.kotlin.aiblogdraft.api.domain

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service

@Service
class DraftService(
    private val draftKeyAppender: DraftKeyAppender,
) {
    private val log = KotlinLogging.logger {}

    fun appendKey(userId: Long): String {
        val key = draftKeyAppender.appendKey(AppendDraftKey(userId))
        log.info("user($userId) appendKey: $key)")
    }
}
