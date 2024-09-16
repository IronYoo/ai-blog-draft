package com.kotlin.aiblogdraft.api.exception

open class DraftException(
    message: String,
) : RuntimeException(message)

class DraftNotFoundException : DraftException("존재하지 않는 초안입니다.")

class DraftUnAuthorizedException : DraftException("접근할 수 없는 초안입니다.")
