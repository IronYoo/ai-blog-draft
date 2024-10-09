package com.kotlin.aiblogdraft.api.common

import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Component

@Component
class WebCookieBuilder {
    private val cookieAge = 60 * 60 * 24

    fun build(token: String): ResponseCookie {
        val cookieBuilder =
            ResponseCookie
                .from("ABDKN", token)
                .path("/")
                .httpOnly(true)
                .maxAge(cookieAge.toLong())

        return cookieBuilder.build()
    }
}
