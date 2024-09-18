package com.kotlin.aiblogdraft.api.exception

class DraftKeyNotFoundException : ApiException(ExceptionType.DRAFT_KEY_NOT_FOUNT, "존재하지 않는 초안 키 입니다.")

class DraftKeyNotAllowedException : ApiException(ExceptionType.DRAFT_KEY_UNAUTHORIZED, "사용 권한이 없는 초안 키 입니다")
