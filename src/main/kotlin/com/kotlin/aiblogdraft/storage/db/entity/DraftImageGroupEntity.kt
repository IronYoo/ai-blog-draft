package com.kotlin.aiblogdraft.storage.db.entity

import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "draft_image_group")
class DraftImageGroupEntity(
    draftKeyId: Long,
) : BaseEntity() {
    var draftKeyId: Long = draftKeyId
        protected set
}
