package com.kotlin.aiblogdraft.api.controller.v1

import com.kotlin.aiblogdraft.api.controller.v1.request.CreateDraftKeyRequest
import com.kotlin.aiblogdraft.api.domain.DraftService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/draft")
class DraftController(
    private val draftService: DraftService,
) {
    private val log = KotlinLogging.logger {}

    @PostMapping("/key")
    fun createDraftKey(
        @RequestBody body: CreateDraftKeyRequest,
    ): String {
        val userId = body.userId
        val key = draftService.appendKey(userId)
        log.info { "user(${body.userId}) issued draftKey($key)" }
        return key
    }
}
