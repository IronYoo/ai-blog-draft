package com.kotlin.aiblogdraft.cloud.sqs.message

import com.fasterxml.jackson.databind.ObjectMapper

abstract class SqsMessage {
    private val objectMapper: ObjectMapper = ObjectMapper()

    fun serialize() = objectMapper.writeValueAsString(this)
}
