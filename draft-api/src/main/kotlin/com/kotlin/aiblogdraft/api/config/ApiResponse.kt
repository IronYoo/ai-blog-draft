package com.kotlin.aiblogdraft.api.config

import com.kotlin.aiblogdraft.api.exception.ExceptionType
import io.swagger.v3.oas.annotations.media.Schema

data class ApiResponse<T>(
    @Schema(description = "요청 결과", required = true)
    val result: ResultType,
    @Schema(description = "응답 데이터", required = false)
    val data: T? = null,
    @Schema(description = "응답 메시지", required = false)
    val message: String? = null,
    @Schema(description = "에러 정보", required = false)
    val error: ErrorDetail? = null,
) {
    data class ErrorDetail(
        @Schema(description = "에러 타입", required = true)
        val type: ExceptionType,
        @Schema(description = "에러 데이터", required = true)
        val data: Any? = null,
    )

    companion object {
        fun success() =
            ApiResponse(
                ResultType.SUCCESS,
                null,
                null,
                null,
            )

        fun <S> success(data: S) = ApiResponse(ResultType.SUCCESS, data, null)

        fun <S> error(
            type: ExceptionType,
            data: Any? = null,
            message: String? = null,
        ): ApiResponse<S> = ApiResponse(ResultType.ERROR, null, message ?: type.message, ErrorDetail(type, data))
    }
}
