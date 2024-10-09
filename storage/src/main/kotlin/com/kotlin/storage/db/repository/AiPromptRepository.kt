package com.kotlin.storage.db.repository

import com.kotlin.storage.db.enum.AiPromptEntityType
import org.springframework.data.jpa.repository.JpaRepository

interface AiPromptRepository : JpaRepository<com.kotlin.storage.db.entity.AiPromptEntity, Long> {
    fun findFirstByType(type: AiPromptEntityType): com.kotlin.storage.db.entity.AiPromptEntity?
}
