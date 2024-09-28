package com.kotlin.aiblogdraft.api.controller.v1.request

import com.kotlin.aiblogdraft.api.domain.auth.dto.Login

data class LoginRequest(
    val email: String,
    val password: String,
) {
    fun toLogin() = Login(email, password)
}
