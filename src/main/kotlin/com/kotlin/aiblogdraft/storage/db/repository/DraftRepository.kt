package com.kotlin.aiblogdraft.storage.db.repository

import com.kotlin.aiblogdraft.storage.db.entity.DraftEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DraftRepository : JpaRepository<DraftEntity, Long> {
    fun findByUserId(userId: Long): List<DraftEntity>
}
