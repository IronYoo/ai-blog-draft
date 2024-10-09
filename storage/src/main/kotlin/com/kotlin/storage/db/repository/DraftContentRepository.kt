package com.kotlin.storage.db.repository

import com.kotlin.storage.db.entity.DraftContentEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DraftContentRepository : JpaRepository<DraftContentEntity, Long>
