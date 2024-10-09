package com.kotlin.aiblogdraft.api.domain.auth.dto

import com.kotlin.storage.db.entity.UserEntity

data class SignUp(
    val email: String,
    val password: String,
    var username: String,
) {
    fun toUserEntity() =
        UserEntity(
            username = username,
            email = email,
            password = password,
        )
}
