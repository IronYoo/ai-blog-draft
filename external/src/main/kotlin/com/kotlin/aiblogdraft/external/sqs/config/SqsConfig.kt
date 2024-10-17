package com.kotlin.aiblogdraft.external.sqs.config

import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory
import io.awspring.cloud.sqs.listener.acknowledgement.handler.AcknowledgementMode
import io.awspring.cloud.sqs.operations.SqsTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import software.amazon.awssdk.auth.credentials.AwsCredentials
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsAsyncClient
import java.net.URI

@Configuration
@Profile("!test")
class SqsConfig {
    @Value("\${spring.cloud.aws.credentials.access-key}")
    lateinit var accessKey: String

    @Value("\${spring.cloud.aws.credentials.secret-key}")
    lateinit var secretKey: String

    @Value("\${spring.cloud.aws.sqs.region}")
    lateinit var region: String

    @Value("\${sqs.url}")
    lateinit var url: String

    private fun credentialProvider(): AwsCredentials =
        object : AwsCredentials {
            override fun accessKeyId(): String = accessKey ?: ""

            override fun secretAccessKey(): String = secretKey ?: ""
        }

    @Bean
    fun sqsAsyncClient(): SqsAsyncClient {
        val client =
            SqsAsyncClient
                .builder()

        return client
            .credentialsProvider(this::credentialProvider)
            .endpointOverride(URI.create(url))
            .region(Region.of(region))
            .build()
    }

    @Bean
    fun defaultSqsListenerContainerFactory(): SqsMessageListenerContainerFactory<Any> =
        SqsMessageListenerContainerFactory
            .builder<Any>()
            .configure { opt ->
                opt.acknowledgementMode(AcknowledgementMode.MANUAL)
            }.sqsAsyncClient(sqsAsyncClient())
            .build()

    @Bean
    fun sqsTemplate(): SqsTemplate = SqsTemplate.newTemplate(sqsAsyncClient())
}
