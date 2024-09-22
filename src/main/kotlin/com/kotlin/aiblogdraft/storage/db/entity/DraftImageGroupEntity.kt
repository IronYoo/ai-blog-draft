package com.kotlin.aiblogdraft.storage.db.entity

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
        println(draftId)
        this.draftId = draftId
        println(this.draftId)
    }
}
