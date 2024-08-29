package com.kotlin.aiblogdraftapi.api.controller.v1

import com.kotlin.aiblogdraftapi.api.controller.v1.request.CreateDraftKeyRequest
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/draft")
class DraftController {
    private val log = KotlinLogging.logger {}

    @PostMapping("/key")
    fun createDraftKey(
        @RequestBody body: CreateDraftKeyRequest,
    ): String {
        log.debug { body }
        return "key"
    }
}
