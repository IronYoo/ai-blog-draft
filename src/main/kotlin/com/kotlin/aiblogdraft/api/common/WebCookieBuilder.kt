package com.kotlin.aiblogdraft.api.common

import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Component

@Component
class WebCookieBuilder {
    private val cookieAge = 60 * 60 * 24

    fun isLocalEnvironment(): Boolean {
        val activeProfiles = System.getProperty("spring.profiles.active")
        return activeProfiles?.contains("local") ?: true
    }

    fun build(token: String): ResponseCookie {
        val isLocalEnv = isLocalEnvironment()
        val cookieBuilder =
            ResponseCookie
                .from("ABDKN", token)
                .path("/")
                .httpOnly(true)
                .maxAge(cookieAge.toLong())

        if (!isLocalEnv) {
            cookieBuilder.sameSite("None").secure(true)
        }

        return cookieBuilder.build()
    }
}
