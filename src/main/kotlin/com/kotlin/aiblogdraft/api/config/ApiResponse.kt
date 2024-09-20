package com.kotlin.aiblogdraft.api.config

import com.kotlin.aiblogdraft.api.exception.ApiException
import com.kotlin.aiblogdraft.api.exception.ExceptionType

data class ApiResponse<T>(
    val result: ResultType,
    val data: T? = null,
    val message: String?,
    val error: ApiException? = null,
) {
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
            error: ExceptionType,
            data: Any? = null,
        ): ApiResponse<S> = ApiResponse(ResultType.ERROR, null, error.message, ApiException(error, data))
    }
}
