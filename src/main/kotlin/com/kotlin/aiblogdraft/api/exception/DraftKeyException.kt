package com.kotlin.aiblogdraft.api.exception

open class DraftKeyException(
    message: String,
) : RuntimeException(message)

class DraftKeyAppendFailException : DraftKeyException("초안 작성 키 발급에 실패했습니다")

class DraftKeyNotAllowedException : DraftKeyException("사용 권한이 없는 초안 키 입니다")
