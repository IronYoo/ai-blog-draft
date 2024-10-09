package com.kotlin.aiblogdraft.storage.db.repository

import com.kotlin.aiblogdraft.storage.db.entity.DraftImageGroupEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DraftImageGroupRepository : JpaRepository<DraftImageGroupEntity, Long> {
    fun findAllByDraftTempId(tempId: Long): List<DraftImageGroupEntity>
}
