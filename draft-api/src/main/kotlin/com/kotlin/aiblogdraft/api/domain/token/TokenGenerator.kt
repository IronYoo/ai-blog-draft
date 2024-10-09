package com.kotlin.aiblogdraft.api.domain.token

import com.kotlin.storage.db.entity.WebUserTokenEntity
import com.kotlin.storage.db.repository.WebUserTokenRepository
import org.springframework.stereotype.Component
import java.security.SecureRandom
import java.util.Base64

@Component
class TokenGenerator(
    private val webUserTokenRepository: WebUserTokenRepository,
) {
    private val secureRandom = SecureRandom()

    fun generateToken(userId: Long): WebUserTokenEntity {
        val token = generateRandomToken()
        val webUserToken = WebUserTokenEntity(userId = userId, token = token)
        return webUserTokenRepository.save(webUserToken)
    }

    private fun generateRandomToken(): String {
        val bytes = ByteArray(24)
        secureRandom.nextBytes(bytes)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes)
    }
}
