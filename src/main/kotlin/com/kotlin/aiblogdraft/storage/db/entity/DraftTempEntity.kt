package com.kotlin.aiblogdraft.storage.db.entity

import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "draft_temp")
class DraftTempEntity(
    userId: Long,
) : BaseEntity() {
    var userId: Long = userId
        protected set
}
