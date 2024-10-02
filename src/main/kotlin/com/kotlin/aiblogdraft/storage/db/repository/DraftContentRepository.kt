package com.kotlin.aiblogdraft.storage.db.repository

import com.kotlin.aiblogdraft.storage.db.entity.DraftContentEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DraftContentRepository : JpaRepository<DraftContentEntity, Long>
