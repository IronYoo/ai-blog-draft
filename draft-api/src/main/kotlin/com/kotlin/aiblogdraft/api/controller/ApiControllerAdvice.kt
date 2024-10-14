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
import org.springframework.web.servlet.resource.NoResourceFoundException

@RestControllerAdvice
class ApiControllerAdvice {
    private val log = KotlinLogging.logger {}

    @ExceptionHandler(ApiException::class)
    fun handleCoreApiException(e: ApiException): ResponseEntity<ApiResponse<Nothing>> {
        when (e.type.logLevel) {
            LogLevel.ERROR -> log.error { "[${e.message}] ${e.stackTraceToString()}" }
            LogLevel.WARN -> log.warn { "[${e.message}] ${e.stackTraceToString()}" }
            else -> log.info { "[${e.message}] ${e.stackTraceToString()}" }
        }
        return ResponseEntity(ApiResponse.error(e.type, e.data), e.type.status)
    }

    @ExceptionHandler(NoResourceFoundException::class)
    fun notFoundException(e: NoResourceFoundException): ResponseEntity<ApiResponse<Any>> {
        log.info { "[${e.message}] $e, ${e.stackTraceToString()}" }
        return ResponseEntity(ApiResponse.error(ExceptionType.NOT_FOUND), ExceptionType.NOT_FOUND.status)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ApiResponse<Any>> {
        log.error { "[${e.message}] $e, ${e.stackTraceToString()}" }
        return ResponseEntity(ApiResponse.error(ExceptionType.DEFAULT_ERROR), ExceptionType.DEFAULT_ERROR.status)
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
                type = ExceptionType.VALIDATION_ERROR,
                message = errors.joinToString(),
            ),
            ExceptionType.VALIDATION_ERROR.status,
        )
    }
}
