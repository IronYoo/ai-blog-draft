package com.kotlin.aiblogdraft.external.sqs.message

import com.fasterxml.jackson.annotation.JsonProperty

data class SqsRecord(
    @JsonProperty("messageId")
    val messageId: String,
    @JsonProperty("receiptHandle")
    val receiptHandle: String,
    @JsonProperty("body")
    val body: String,
    @JsonProperty("attributes")
    val attributes: SqsAttribute,
    @JsonProperty("messageAttributes")
    val messageAttributes: Map<String, Any>,
    @JsonProperty("md5OfBody")
    val md5OfBody: String,
    @JsonProperty("eventSource")
    val eventSource: String,
    @JsonProperty("eventSourceARN")
    val eventSourceArn: String,
    @JsonProperty("awsRegion")
    val awsRegion: String,
)
