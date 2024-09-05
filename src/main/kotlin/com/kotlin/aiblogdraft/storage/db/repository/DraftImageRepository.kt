package com.kotlin.aiblogdraft.storage.db.repository

import com.kotlin.aiblogdraft.storage.db.entity.DraftImageEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DraftImageRepository : JpaRepository<DraftImageEntity, Long>
