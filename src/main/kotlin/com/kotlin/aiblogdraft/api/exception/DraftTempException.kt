package com.kotlin.aiblogdraft.api.exception

class DraftTempNotFoundException : ApiException(ExceptionType.DRAFT_TEMP_NOT_FOUNT, "존재하지 않는 임시 초안입니다.")

class DraftTempNotAllowedException : ApiException(ExceptionType.DRAFT_TEMP_UNAUTHORIZED, "사용 권한이 없는 임시 초안입니다")
