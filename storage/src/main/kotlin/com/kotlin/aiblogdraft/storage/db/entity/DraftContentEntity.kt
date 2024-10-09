package com.kotlin.aiblogdraft.storage.db.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "draft_content")
class DraftContentEntity(
    content: String,
    imageGroupId: Long,
) : BaseEntity() {
    @Column(columnDefinition = "TEXT")
    var content: String = content
        protected set

    var imageGroupId: Long = imageGroupId
        protected set
}
