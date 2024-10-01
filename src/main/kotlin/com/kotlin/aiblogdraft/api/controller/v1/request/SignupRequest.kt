package com.kotlin.aiblogdraft.api.controller.v1.request

import com.kotlin.aiblogdraft.api.domain.auth.dto.SignUp
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email

@Schema(description = "회원가입 요청")
class SignupRequest(
    @Schema(description = "유저 닉네임", example = "mocha", required = true)
    val username: String,
    @Schema(description = "암호", example = "abc123", required = true)
    val password: String,
    @Schema(description = "이메일", example = "a@email.com", required = true)
    @field:Email
    val email: String,
) {
    fun toSignUp() =
        SignUp(
            username = username,
            password = password,
            email = email,
        )
}
