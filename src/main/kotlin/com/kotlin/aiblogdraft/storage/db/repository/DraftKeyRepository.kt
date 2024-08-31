package com.kotlin.aiblogdraft.storage.db.repository

import com.kotlin.aiblogdraft.storage.db.entity.DraftKeyEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DraftKeyRepository : JpaRepository<DraftKeyEntity, Long> {
    fun findByKey(key: String): DraftKeyEntity?
}
