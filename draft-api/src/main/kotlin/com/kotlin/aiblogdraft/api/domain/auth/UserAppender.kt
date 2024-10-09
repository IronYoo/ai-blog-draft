package com.kotlin.aiblogdraft.api.domain.auth

import com.kotlin.aiblogdraft.api.exception.EmailDuplicationException
import com.kotlin.aiblogdraft.storage.db.entity.UserEntity
import com.kotlin.aiblogdraft.storage.db.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class UserAppender(
    private val userRepository: UserRepository,
    private val passwordManager: PasswordManager,
) {
    private fun validate(user: UserEntity) {
        userRepository.findByEmail(user.email)?.let { throw EmailDuplicationException() }
    }

    private fun hashPassword(user: UserEntity) {
        val salt = passwordManager.generateSalt()
        val hashedPassword = passwordManager.hash(user.password, salt)
        user.updatePassword("$salt:$hashedPassword")
    }

    fun append(user: UserEntity) {
        validate(user)
        hashPassword(user)
        userRepository.save(user)
    }
}
