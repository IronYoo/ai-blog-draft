package com.kotlin.aiblogdraft.api.exception

open class ApiException(
    message: String,
    val data: Any? = null,
) : RuntimeException(message) {
    constructor(error: ExceptionType, data: Any? = null) : this (
        message = error.message,
        data = data,
    )
}
