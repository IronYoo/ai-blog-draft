package com.kotlin.aiblogdraft.api.config

import jakarta.servlet.FilterChain
import jakarta.servlet.ReadListener
import jakarta.servlet.ServletInputStream
import jakarta.servlet.ServletOutputStream
import jakarta.servlet.WriteListener
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpServletResponseWrapper
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.ByteArrayOutputStream
import java.nio.charset.StandardCharsets
import java.util.UUID

@Component
class MdcFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        try {
            val traceId = UUID.randomUUID().toString()
            MDC.put("traceId", traceId)

            val wrappedRequest = CustomHttpServletRequestWrapper(request as HttpServletRequest)
            val wrappedResponse = CustomHttpServletResponseWrapper(response as HttpServletResponse)

            logRequest(wrappedRequest)
            filterChain.doFilter(request, response)
            logResponse(wrappedResponse)
        } finally {
            MDC.clear()
        }
    }

    private fun logRequest(request: CustomHttpServletRequestWrapper) {
        val headers = request.headerNames.toList().joinToString { name -> "$name: ${request.getHeader(name)}" }
        val body = request.body

        logger.info("request: ${request.method} ${request.requestURI}, Headers: $headers, Body: $body")
    }

    private fun logResponse(response: CustomHttpServletResponseWrapper) {
        val headers = response.headerNames.joinToString { name -> "$name: ${response.getHeader(name)}" }
        val body = String(response.toByteArray(), StandardCharsets.UTF_8)

        logger.info("response: ${response.status}, Headers: $headers, Body: $body")
    }
}

class CustomHttpServletRequestWrapper(
    request: HttpServletRequest,
) : HttpServletRequestWrapper(request) {
    val body: String

    init {
        val inputStream = request.inputStream
        val bodyBytes = inputStream.readBytes()
        body = String(bodyBytes, StandardCharsets.UTF_8)
    }

    override fun getInputStream(): ServletInputStream =
        object : ServletInputStream() {
            private val inputStream = body.byteInputStream()

            override fun read(): Int = inputStream.read()

            override fun isFinished(): Boolean = inputStream.available() == 0

            override fun isReady(): Boolean = true

            override fun setReadListener(readListener: ReadListener?) {}
        }
}

class CustomHttpServletResponseWrapper(
    response: HttpServletResponse,
) : HttpServletResponseWrapper(response) {
    private val outputStream = ByteArrayOutputStream()
    private val servletOutputStream =
        object : ServletOutputStream() {
            override fun write(b: Int) {
                outputStream.write(b)
            }

            override fun isReady(): Boolean = true

            override fun setWriteListener(writeListener: WriteListener?) {}
        }

    override fun getOutputStream(): ServletOutputStream = servletOutputStream

    fun toByteArray(): ByteArray = outputStream.toByteArray()
}
