package com.kotlin.aiblogdraft.cloud.sqs

enum class QueueName(
    val customName: String,
) {
    DRAFT_CONTENT_JOB_QUEUE("draft-content-job-queue"),
}
