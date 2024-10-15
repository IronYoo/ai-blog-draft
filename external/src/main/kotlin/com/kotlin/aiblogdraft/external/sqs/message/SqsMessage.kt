package com.kotlin.aiblogdraft.external.sqs.message

import com.fasterxml.jackson.annotation.JsonProperty

data class SqsMessage(
    @JsonProperty("Records")
    val records: List<SqsRecord>,
)
