package com.kotlin.aiblogdraft.api.domain

open class DraftKeyException(
    message: String,
) : RuntimeException(message)

class DraftKeyNotFoundException : DraftKeyException("존재하지 않는 초안 키 입니다.")
