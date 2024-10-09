package com.kotlin.aiblogdraft.api.common

import com.kotlin.aiblogdraft.api.exception.ExpiredTokenException
import com.kotlin.aiblogdraft.api.exception.UnAuthorizedException
import com.kotlin.storage.db.repository.WebUserTokenRepository
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import java.time.LocalDateTime

@Component
class WebUserArgumentResolver(
    private val webUserTokenRepository: WebUserTokenRepository,
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean = parameter.parameterType == WebUser::class.java

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): WebUser {
        val request = webRequest.getNativeRequest(HttpServletRequest::class.java)

        val cookies = request?.cookies ?: arrayOf()
        val token = cookies.find { it.name == "ABDKN" } ?: throw UnAuthorizedException()
        val userToken = webUserTokenRepository.findByToken(token.value) ?: throw UnAuthorizedException()

        if (userToken.expireAt.isBefore(LocalDateTime.now())) {
            throw ExpiredTokenException()
        }
        return WebUser(userToken.userId)
    }
}
