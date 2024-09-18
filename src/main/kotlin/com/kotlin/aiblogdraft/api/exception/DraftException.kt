package com.kotlin.aiblogdraft.api.exception

class DraftNotFoundException : ApiException(ExceptionType.DRAFT_NOT_FOUNT)

class DraftUnAuthorizedException : ApiException(ExceptionType.DRAFT_UNAUTHORIZED)
