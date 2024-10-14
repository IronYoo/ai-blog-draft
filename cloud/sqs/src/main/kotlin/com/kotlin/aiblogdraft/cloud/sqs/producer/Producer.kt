package com.kotlin.aiblogdraft.cloud.sqs.producer

import com.kotlin.aiblogdraft.cloud.sqs.QueueName
import com.kotlin.aiblogdraft.cloud.sqs.message.BaseMessage
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
