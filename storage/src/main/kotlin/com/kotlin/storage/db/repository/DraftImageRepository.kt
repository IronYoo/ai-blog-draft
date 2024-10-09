package com.kotlin.storage.db.repository

import com.kotlin.storage.db.entity.DraftImageEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DraftImageRepository : JpaRepository<DraftImageEntity, Long> {
    fun countByDraftImageGroupId(draftImageGroupId: Long): Int
}
