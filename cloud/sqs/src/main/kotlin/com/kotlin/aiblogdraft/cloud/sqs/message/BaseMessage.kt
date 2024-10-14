package com.kotlin.aiblogdraft.cloud.sqs.message

import com.fasterxml.jackson.databind.ObjectMapper

abstract class BaseMessage {
    private val objectMapper: ObjectMapper = ObjectMapper()

    fun serialize(): String = objectMapper.writeValueAsString(this)
}
