package com.kotlin.aiblogdraft.draftclient

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AuthWeb {
    @GetMapping("/signup")
    fun signup() = "signup"

    @GetMapping("/login")
    fun login() = "login"
}
