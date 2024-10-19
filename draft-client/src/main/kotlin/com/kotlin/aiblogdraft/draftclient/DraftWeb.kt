package com.kotlin.aiblogdraft.draftclient

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class DraftWeb {
    @GetMapping("/draft")
    fun postDraft() = "postDraft"

    @GetMapping("/drafts/{id}")
    fun draft(
        @PathVariable id: String,
    ) = "draft"
}
