package com.kotlin.storage.db.repository

import com.kotlin.storage.db.entity.WebUserTokenEntity
import org.springframework.data.jpa.repository.JpaRepository

interface WebUserTokenRepository : JpaRepository<WebUserTokenEntity, Long> {
    fun findByToken(token: String): WebUserTokenEntity?
}
