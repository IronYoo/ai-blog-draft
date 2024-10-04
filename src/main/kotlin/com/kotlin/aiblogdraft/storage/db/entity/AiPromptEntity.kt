package com.kotlin.aiblogdraft.storage.db.entity

import com.kotlin.aiblogdraft.storage.db.enum.AiPromptEntityType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table

@Entity
@Table(name = "ai_prompt")
class AiPromptEntity(
    text: String,
    type: AiPromptEntityType,
) : BaseEntity() {
    @Column(columnDefinition = "TEXT")
    var promptText = text
        protected set

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(30)")
    var type = type
        protected set
}
