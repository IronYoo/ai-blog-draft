package com.kotlin.aiblogdraft.storage.db.repository

import com.kotlin.aiblogdraft.storage.db.entity.WebUserTokenEntity
import org.springframework.data.jpa.repository.JpaRepository

interface WebUserTokenRepository : JpaRepository<WebUserTokenEntity, Long> {
    fun findByToken(token: String): WebUserTokenEntity?
}
