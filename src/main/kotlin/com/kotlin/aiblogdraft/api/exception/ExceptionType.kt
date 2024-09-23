package com.kotlin.aiblogdraft.api.exception

import org.springframework.boot.logging.LogLevel
import org.springframework.http.HttpStatus

enum class ExceptionType(
    val status: HttpStatus,
    val message: String,
    val logLevel: LogLevel,
) {
    DEFAULT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "internal server error occurred", LogLevel.ERROR),

    // Draft Exception
    DRAFT_NOT_FOUNT(HttpStatus.NOT_FOUND, "존재하지 않는 초안입니다", LogLevel.WARN),
    DRAFT_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "접근할 수 없는 초안입니다", LogLevel.WARN),

    // Draft Temp Exception
    DRAFT_TEMP_NOT_FOUNT(HttpStatus.NOT_FOUND, "존재하지 않는 초안 키 입니다", LogLevel.WARN),
    DRAFT_TEMP_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "사용 권한이 없는 초안 키 입니다", LogLevel.WARN),

    // Validation
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "잘못된 요청입니다", LogLevel.WARN),

    // Draft Image Exception
    DRAFT_IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 이미지입니다", LogLevel.WARN),
    DRAFT_IMAGE_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "접근할 수 없는 이미지입니다", LogLevel.WARN),
}
