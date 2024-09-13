package com.kotlin.aiblogdraft.api.exception

open class DraftException(
    message: String,
) : RuntimeException(message)

class DraftNotFoundException : DraftException("존재하지 않는 초안입니다.")

class DraftIsNotDone : DraftException("아직 처리되지 않은 초안입니다.")
