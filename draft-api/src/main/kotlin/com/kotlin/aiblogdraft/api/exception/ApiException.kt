package com.kotlin.aiblogdraft.api.exception

open class ApiException(
    val data: Any? = null,
    val type: ExceptionType,
) : RuntimeException(type.message) {
    constructor(error: ExceptionType, data: Any? = null) : this (
        data = data,
        type = error,
    )
}
