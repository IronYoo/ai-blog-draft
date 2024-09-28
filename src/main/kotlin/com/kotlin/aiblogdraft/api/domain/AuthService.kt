package com.kotlin.aiblogdraft.api.domain

import com.kotlin.aiblogdraft.api.domain.auth.LoginUserSearcher
import com.kotlin.aiblogdraft.api.domain.auth.UserAppender
import com.kotlin.aiblogdraft.api.domain.auth.dto.Login
import com.kotlin.aiblogdraft.api.domain.auth.dto.SignUp
import com.kotlin.aiblogdraft.api.domain.token.TokenGenerator
import com.kotlin.aiblogdraft.storage.db.entity.WebUserTokenEntity
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val loginUserSearcher: LoginUserSearcher,
    private val userAppender: UserAppender,
    private val tokenGenerator: TokenGenerator,
) {
    fun signup(signUp: SignUp) {
        userAppender.append(signUp.toUserEntity())
    }

    fun login(login: Login): WebUserTokenEntity {
        val foundUser = loginUserSearcher.search(login)
        return tokenGenerator.generateToken(foundUser.id)
    }
}
