package com.kotlin.aiblogdraft.storage.db.entity

import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "web_user_token")
class WebUserTokenEntity(
    userId: Long,
    token: String,
) : BaseEntity() {
    var userId: Long = userId
        protected set

    var token: String = token
        protected set

    var expireAt: LocalDateTime = LocalDateTime.now().plusHours(24L)
}
