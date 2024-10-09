package com.kotlin.aiblogdraft.api.domain.auth

import org.springframework.stereotype.Component
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Base64

@Component
class PasswordManager {
    fun generateSalt(): String {
        val saltBytes = ByteArray(16)
        SecureRandom().nextBytes(saltBytes)
        return Base64.getEncoder().encodeToString(saltBytes)
    }

    fun hash(
        password: String,
        salt: String,
    ): String {
        val md = MessageDigest.getInstance("SHA-256")
        val hashedBytes = md.digest((salt + password).toByteArray(Charsets.UTF_8))
        return Base64.getEncoder().encodeToString(hashedBytes)
    }

    fun checkPassword(
        plainPassword: String,
        salt: String,
        storedHash: String,
    ): Boolean {
        val hash = hash(plainPassword, salt)
        return hash == storedHash
    }
}
