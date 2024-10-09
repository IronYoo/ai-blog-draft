package com.kotlin.aiblogdraft.api.config

import org.springframework.ai.chat.client.ChatClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AiConfig {
    @Bean
    fun chatClient(builder: ChatClient.Builder): ChatClient =
        builder.defaultSystem("you are blogger that answers question in korean.").build()
}
