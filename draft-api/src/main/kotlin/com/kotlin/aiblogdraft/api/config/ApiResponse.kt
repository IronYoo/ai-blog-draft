package com.kotlin.aiblogdraft.api.config

import com.kotlin.aiblogdraft.api.exception.ExceptionType

data class ApiResponse<T>(
    val result: ResultType,
    val data: T? = null,
    val message: String?,
    val error: ErrorDetail? = null,
) {
    data class ErrorDetail(
        val type: ExceptionType,
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
