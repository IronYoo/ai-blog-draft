package com.kotlin.aiblogdraft.api.exception

open class DraftException(
    message: String,
) : RuntimeException(message)

class DraftNotFoundException : DraftException("존재하지 않는 초안입니다.")
