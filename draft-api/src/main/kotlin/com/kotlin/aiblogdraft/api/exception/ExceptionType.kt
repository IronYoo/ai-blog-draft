package com.kotlin.aiblogdraft.api.exception

import org.springframework.boot.logging.LogLevel
import org.springframework.http.HttpStatus

enum class ExceptionType(
    val status: HttpStatus,
    val message: String,
    val logLevel: LogLevel,
) {
    // 기본
    NOT_FOUND(HttpStatus.NOT_FOUND, "not found", LogLevel.INFO),

    DEFAULT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "internal server error occurred", LogLevel.ERROR),

    // Draft Exception
    DRAFT_NOT_FOUNT(HttpStatus.NOT_FOUND, "존재하지 않는 초안입니다", LogLevel.WARN),
    DRAFT_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "접근할 수 없는 초안입니다", LogLevel.WARN),
    DRAFT_NOT_PROCESSED(HttpStatus.BAD_REQUEST, "처리되지 않은 초안입니다.", LogLevel.WARN),
    DRAFT_NO_IMAGES(HttpStatus.BAD_REQUEST, "이미지가 없는 초안을 등록할 수 없습니다.", LogLevel.WARN),

    // Draft Temp Exception
    DRAFT_TEMP_NOT_FOUNT(HttpStatus.NOT_FOUND, "존재하지 않는 초안 키 입니다", LogLevel.WARN),
    DRAFT_TEMP_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "사용 권한이 없는 초안 키 입니다", LogLevel.WARN),

    // Validation
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "잘못된 요청입니다", LogLevel.WARN),

    // Draft Image Exception
    DRAFT_IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 이미지입니다", LogLevel.WARN),
    DRAFT_IMAGE_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "접근할 수 없는 이미지입니다", LogLevel.WARN),
    DRAFT_IMAGE_MODIFICATION_DENIED(HttpStatus.BAD_REQUEST, "수정할 수 없는 이미지입니다.", LogLevel.WARN),
    DRAFT_IMAGE_NOT_ALLOWED_EXTENSION(HttpStatus.BAD_REQUEST, "사용할 수 없는 초안 이미지 형식 입니다.", LogLevel.WARN),

    // Auth Exception
    EMAIL_DUPLICATE_EXCEPTION(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다.", LogLevel.WARN),
    LOGIN_FAIL_EXCEPTION(HttpStatus.BAD_REQUEST, "잘못된 이메일 또는 비밀번호입니다.", LogLevel.WARN),
    UNAUTHORIZED_EXCEPTION(HttpStatus.UNAUTHORIZED, "인증되지 않은 유저입니다.", LogLevel.WARN),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다.", LogLevel.WARN),
}
