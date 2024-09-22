package com.kotlin.aiblogdraft.storage.db.repository

import com.kotlin.aiblogdraft.storage.db.entity.DraftTempEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DraftTempRepository : JpaRepository<DraftTempEntity, Long>
