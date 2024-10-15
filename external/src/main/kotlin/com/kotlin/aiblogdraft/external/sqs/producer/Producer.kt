package com.kotlin.aiblogdraft.external.sqs.producer

import com.kotlin.aiblogdraft.external.sqs.QueueName
import com.kotlin.aiblogdraft.external.sqs.message.BaseMessage
import io.awspring.cloud.sqs.operations.SqsTemplate
import org.springframework.stereotype.Component

@Component
class Producer(
    private val sqsTemplate: SqsTemplate,
) {
    fun send(
        queueName: QueueName,
        message: BaseMessage,
    ) {
        sqsTemplate.send { sendOpsTo ->
            sendOpsTo
                .queue(queueName.customName)
                .payload(message.serialize())
        }
    }
}
