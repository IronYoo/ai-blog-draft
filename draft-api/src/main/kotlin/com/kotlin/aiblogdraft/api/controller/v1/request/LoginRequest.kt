package com.kotlin.aiblogdraft.api.controller.v1.request

import com.kotlin.aiblogdraft.api.domain.auth.dto.Login
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email

@Schema(description = "로그인 요청")
data class LoginRequest(
    @Schema(description = "이메일", example = "a@email.com", required = true)
    @field:Email
    val email: String,
    @Schema(description = "암호", example = "abc123", required = true)
    val password: String,
) {
    fun toLogin() = Login(email, password)
}
