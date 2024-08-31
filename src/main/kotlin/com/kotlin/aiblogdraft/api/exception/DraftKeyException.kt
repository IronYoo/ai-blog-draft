package com.kotlin.aiblogdraft.api.exception

open class DraftKeyException(
    message: String,
) : RuntimeException(message)

class DraftKeyAppendFailException : DraftKeyException("초안 작성 키 발급에 실패했습니다")
