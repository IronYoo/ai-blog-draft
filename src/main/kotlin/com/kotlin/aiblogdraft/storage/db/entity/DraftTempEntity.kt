package com.kotlin.aiblogdraft.storage.db.entity

import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "draft_temp")
class DraftTempEntity(
    userId: Long,
) : BaseEntity() {
    var userId: Long = userId
        protected set

    var expireAt: LocalDateTime? = null
        protected set
}
