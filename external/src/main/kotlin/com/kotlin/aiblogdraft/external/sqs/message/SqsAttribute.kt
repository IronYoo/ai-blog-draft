package com.kotlin.aiblogdraft.external.sqs.message

import com.fasterxml.jackson.annotation.JsonProperty

class SqsAttribute(
    @JsonProperty("ApproximateReceiveCount")
    val approximateReceiveCount: String,
    @JsonProperty("SentTimestamp")
    val sentTimestamp: String,
    @JsonProperty("SenderId")
    val senderId: String,
    @JsonProperty("ApproximateFirstReceiveTimestamp")
    val approximateFirstReceiveTimestamp: String,
)
