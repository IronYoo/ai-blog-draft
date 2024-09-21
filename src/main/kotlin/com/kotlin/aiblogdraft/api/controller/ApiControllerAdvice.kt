package com.kotlin.aiblogdraft.api.controller

import com.kotlin.aiblogdraft.api.config.ApiResponse
import com.kotlin.aiblogdraft.api.exception.ApiException
import com.kotlin.aiblogdraft.api.exception.ExceptionType
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.logging.LogLevel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiControllerAdvice {
    private val log = KotlinLogging.logger {}

    @ExceptionHandler(ApiException::class)
    fun handleCoreApiException(e: ApiException): ResponseEntity<ApiResponse<Nothing>> {
        when (e.type.logLevel) {
            LogLevel.ERROR -> log.error { "[${e.message}] $e" }
            LogLevel.WARN -> log.warn { "[${e.message}] $e" }
            else -> log.info { "[${e.message}] $e" }
        }
        return ResponseEntity(ApiResponse.error(e.type, e.data), e.type.status)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ApiResponse<Any>> {
        log.error { "[${e.message}] $e" }
        return ResponseEntity(ApiResponse.error(ExceptionType.DRAFT_NOT_FOUNT), ExceptionType.DEFAULT_ERROR.status)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(e: MethodArgumentNotValidException): ResponseEntity<ApiResponse<Any>> {
        val errors =
            e.bindingResult.allErrors.map { error ->
                error.defaultMessage ?: "Invalid value"
            }
        log.warn { "Validation failed: $errors" }
        return ResponseEntity(
            ApiResponse.error(
                error = ExceptionType.VALIDATION_ERROR,
                message = errors.joinToString(),
            ),
            ExceptionType.VALIDATION_ERROR.status,
        )
    }
}
