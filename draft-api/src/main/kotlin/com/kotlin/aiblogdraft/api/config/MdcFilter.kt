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
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
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

            val wrappedRequest = CustomHttpServletRequestWrapper(request)
            val wrappedResponse = CustomHttpServletResponseWrapper(response)

            if (wrappedRequest.requestURI.startsWith("/v1")) {
                logRequest(wrappedRequest)
                filterChain.doFilter(wrappedRequest, wrappedResponse)
                wrappedResponse.copyBodyToResponse()
                logResponse(wrappedResponse)
            } else {
                filterChain.doFilter(wrappedRequest, wrappedResponse)
                wrappedResponse.copyBodyToResponse()
            }
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
    private var isMultipart: Boolean = false

    init {
        isMultipart = request.contentType?.lowercase()?.contains("multipart/form-data") == true
        body =
            if (!isMultipart) {
                request.inputStream.readBytes().toString(StandardCharsets.UTF_8)
            } else {
                ""
            }
    }

    override fun getInputStream(): ServletInputStream =
        if (!isMultipart) {
            val byteArrayInputStream = ByteArrayInputStream(body.toByteArray(StandardCharsets.UTF_8))
            object : ServletInputStream() {
                override fun read(): Int = byteArrayInputStream.read()

                override fun isFinished(): Boolean = byteArrayInputStream.available() == 0

                override fun isReady(): Boolean = true

                override fun setReadListener(readListener: ReadListener?) {}
            }
        } else {
            super.getInputStream()
        }

    override fun getReader(): BufferedReader =
        if (!isMultipart) {
            BufferedReader(InputStreamReader(getInputStream(), StandardCharsets.UTF_8))
        } else {
            super.getReader()
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

    private val printWriter = PrintWriter(OutputStreamWriter(outputStream, StandardCharsets.UTF_8))

    override fun getOutputStream(): ServletOutputStream = servletOutputStream

    override fun getWriter(): PrintWriter = printWriter

    override fun flushBuffer() {
        printWriter.flush()
        super.flushBuffer()
    }

    fun copyBodyToResponse() {
        val responseBytes = outputStream.toByteArray()
        response.outputStream.write(responseBytes)
        response.outputStream.flush()
    }

    fun toByteArray(): ByteArray {
        flushBuffer()
        return outputStream.toByteArray()
    }
}
