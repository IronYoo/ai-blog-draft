package com.kotlin.aiblogdraft.api.exception

class EmailDuplicationException : ApiException(ExceptionType.EMAIL_DUPLICATE_EXCEPTION)

class LoginFailException : ApiException(ExceptionType.LOGIN_FAIL_EXCEPTION)

class UnAuthorizedException : ApiException(ExceptionType.UNAUTHORIZED_EXCEPTION)

class ExpiredTokenException : ApiException(ExceptionType.EXPIRED_TOKEN)
