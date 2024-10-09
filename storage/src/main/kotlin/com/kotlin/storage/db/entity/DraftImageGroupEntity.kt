package com.kotlin.storage.db.entity

import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "draft_image_group")
class DraftImageGroupEntity(
    draftTempId: Long,
) : BaseEntity() {
    var draftId: Long? = null
        protected set

    var draftTempId: Long? = draftTempId
        protected set

    fun updateDraftId(draftId: Long) {
        this.draftId = draftId
    }
}
