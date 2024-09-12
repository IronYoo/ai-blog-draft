package com.kotlin.aiblogdraft.api.exception

open class DraftKeyException(
    message: String,
) : RuntimeException(message)

class DraftKeyNotFoundException : DraftKeyException("존재하지 않는 초안 키 입니다.")

class DraftKeyNotAllowedException : DraftKeyException("사용 권한이 없는 초안 키 입니다")
