package com.kotlin.aiblogdraft.api.exception

class DraftImageNotFoundException : ApiException(ExceptionType.DRAFT_IMAGE_NOT_FOUND)

class DraftImageNotAllowedException : ApiException(ExceptionType.DRAFT_IMAGE_UNAUTHORIZED)
