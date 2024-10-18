package com.kotlin.aiblogdraft.draftclient

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class DisplayWeb {
    @GetMapping("/home")
    fun home(model: Model): String = "index"
}
