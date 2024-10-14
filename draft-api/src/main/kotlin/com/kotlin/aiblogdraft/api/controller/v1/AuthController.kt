package com.kotlin.aiblogdraft.api.controller.v1

import com.kotlin.aiblogdraft.api.common.WebCookieBuilder
import com.kotlin.aiblogdraft.api.config.ApiResponse
import com.kotlin.aiblogdraft.api.controller.v1.request.LoginRequest
import com.kotlin.aiblogdraft.api.controller.v1.request.SignupRequest
import com.kotlin.aiblogdraft.api.domain.AuthService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "AuthController", description = "인증/인가 컨트롤러")
@RestController
@RequestMapping("v1/auth")
class AuthController(
    private val authService: AuthService,
    private val webCookieBuilder: WebCookieBuilder,
) {
    @PostMapping("/signup")
    fun signup(
        @Valid @RequestBody signupRequest: SignupRequest,
    ): ApiResponse<Nothing> {
        authService.signup(signupRequest.toSignUp())
        return ApiResponse.success()
    }

    @PostMapping("/login")
    fun login(
        @Valid @RequestBody loginRequest: LoginRequest,
        response: HttpServletResponse,
    ): ApiResponse<Nothing> {
        val token = authService.login(loginRequest.toLogin()).token
        val cookie = webCookieBuilder.build(token)
        response.addHeader("Set-Cookie", cookie.toString())

        return ApiResponse.success()
    }
}
