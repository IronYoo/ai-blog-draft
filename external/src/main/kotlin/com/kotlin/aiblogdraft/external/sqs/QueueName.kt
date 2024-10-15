package com.kotlin.aiblogdraft.external.sqs

enum class QueueName(
    val customName: String,
) {
    DRAFT_CONTENT_JOB_QUEUE("draft-content-job-queue"),
}
