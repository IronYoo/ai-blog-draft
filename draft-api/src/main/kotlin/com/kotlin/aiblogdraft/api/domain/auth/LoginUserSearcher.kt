package com.kotlin.aiblogdraft.api.domain.auth

import com.kotlin.aiblogdraft.api.domain.auth.dto.Login
import com.kotlin.aiblogdraft.api.exception.LoginFailException
import com.kotlin.storage.db.entity.UserEntity
import com.kotlin.storage.db.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class LoginUserSearcher(
    private val userRepository: UserRepository,
    private val passwordManager: PasswordManager,
) {
    private fun compare(
        password: String,
        salt: String,
        hash: String,
    ) {
        if (!passwordManager.checkPassword(password, salt, hash)) {
            throw LoginFailException()
        }
    }

    fun search(login: Login): UserEntity {
        val user = userRepository.findByEmail(login.email) ?: throw LoginFailException()

        val parts = user.password.split(":")
        val salt = parts[0]
        val hash = parts[1]

        compare(login.password, salt, hash)

        return user
    }
}
