package com.kotlin.aiblogdraft.storage.db.repository

import com.kotlin.aiblogdraft.storage.db.entity.AiPromptEntity
import com.kotlin.aiblogdraft.storage.db.enum.AiPromptEntityType
import org.springframework.data.jpa.repository.JpaRepository

interface AiPromptRepository : JpaRepository<AiPromptEntity, Long> {
    fun findFirstByType(type: AiPromptEntityType): AiPromptEntity?
}
