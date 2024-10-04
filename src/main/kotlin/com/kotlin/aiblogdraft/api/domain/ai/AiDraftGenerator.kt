package com.kotlin.aiblogdraft.api.domain.ai

import com.kotlin.aiblogdraft.api.domain.draft.dto.DraftType
import com.kotlin.aiblogdraft.storage.db.entity.DraftEntity
import com.kotlin.aiblogdraft.storage.db.repository.AiPromptRepository
import com.kotlin.aiblogdraft.storage.db.repository.dto.DraftImageGroup
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.model.Media
import org.springframework.ai.openai.OpenAiChatOptions
import org.springframework.ai.openai.api.OpenAiApi
import org.springframework.stereotype.Component
import java.net.URL

@Component
class AiDraftGenerator(
    private val chatClient: ChatClient,
    private val aiPromptRepository: AiPromptRepository,
) {
    fun generate(
        draft: DraftEntity,
        imageGroup: DraftImageGroup,
    ): String {
        val prompt = getPrompt(draft)
        val userMessage = UserMessage(prompt, imageGroup.images.map { Media(it.imgType, URL(it.url)) })
        val response =
            chatClient
                .prompt(Prompt(userMessage, OpenAiChatOptions.builder().withModel(OpenAiApi.ChatModel.GPT_4_O).build()))
                .call()
                .content()

        return response
    }

    private fun getPrompt(draft: DraftEntity): String {
        val draftType = DraftType.findByEntityType(draft.type)
        val prompt =
            aiPromptRepository
                .findFirstByType(draftType.aiPromptEntityType)!!
                .promptText
                .trimIndent()
        return replace(draftType.description, draft.title, prompt)
    }

    private fun replace(
        description: String,
        title: String,
        prompt: String,
    ): String {
        var replaced = prompt
        val variables =
            mapOf("draftType.description" to description, "draft.title" to title)
        variables.forEach { (key, value) ->
            replaced = replaced.replace("\${$key}", value)
        }

        return replaced
    }
}
