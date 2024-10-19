package com.kotlin.aiblogdraft.draftclient

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class DraftWeb {
    @GetMapping("/draft")
    fun draft() = "draft"
}
