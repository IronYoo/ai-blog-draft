package com.kotlin.aiblogdraft.storage.db.repository

import com.kotlin.aiblogdraft.storage.db.enum.AiPromptEntityType
import org.springframework.data.jpa.repository.JpaRepository

interface AiPromptRepository : JpaRepository<com.kotlin.aiblogdraft.storage.db.entity.AiPromptEntity, Long> {
    fun findFirstByType(type: AiPromptEntityType): com.kotlin.aiblogdraft.storage.db.entity.AiPromptEntity?
}
