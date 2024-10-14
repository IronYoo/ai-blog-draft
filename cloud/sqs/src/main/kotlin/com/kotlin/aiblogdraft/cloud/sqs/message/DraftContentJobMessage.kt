package com.kotlin.aiblogdraft.cloud.sqs.message

import com.fasterxml.jackson.annotation.JsonProperty

data class DraftContentJobMessage(
    @JsonProperty("draftId")
    val draftId: Long = 0,
) : BaseMessage()
