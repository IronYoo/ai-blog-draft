package com.kotlin.aiblogdraft.api.common

import com.kotlin.aiblogdraft.storage.db.repository.UserRepository
import com.kotlin.aiblogdraft.storage.db.repository.WebUserTokenRepository
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import java.time.LocalDateTime

@Component
class UserOrGuestArgumentResolver(
    private val webUserTokenRepository: WebUserTokenRepository,
    private val userRepository: UserRepository,
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean = parameter.parameterType == UserOrGuest::class.java

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): UserOrGuest {
        val request = webRequest.getNativeRequest(HttpServletRequest::class.java)

        val cookies = request?.cookies ?: arrayOf()
        val userToken = cookies.find { it.name == "ABDKN" }?.let { webUserTokenRepository.findByToken(it.value) }

        if (userToken?.expireAt?.isBefore(LocalDateTime.now()) == true) {
            return UserOrGuest(null)
        }
        val user = userToken?.let { userRepository.findByIdOrNull(it.userId) }
        return UserOrGuest(user?.username)
    }
}
