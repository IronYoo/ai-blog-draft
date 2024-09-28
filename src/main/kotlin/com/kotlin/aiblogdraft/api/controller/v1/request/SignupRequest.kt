package com.kotlin.aiblogdraft.api.controller.v1.request

import com.kotlin.aiblogdraft.api.domain.auth.dto.SignUp

class SignupRequest(
    val username: String,
    val password: String,
    val email: String,
) {
    fun toSignUp() =
        SignUp(
            username = username,
            password = password,
            email = email,
        )
}
