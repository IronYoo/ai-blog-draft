package com.kotlin.aiblogdraft.storage.db.entity

import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "draft_image")
class DraftImageEntity(
    url: String,
    draftImageGroupId: Long,
) : BaseEntity() {
    var url: String = url
        protected set

    var draftImageGroupId: Long = draftImageGroupId
        protected set
}
