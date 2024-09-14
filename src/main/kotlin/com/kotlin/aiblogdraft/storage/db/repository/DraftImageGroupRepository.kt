package com.kotlin.aiblogdraft.storage.db.repository

import com.kotlin.aiblogdraft.storage.db.entity.DraftImageGroupEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface DraftImageGroupRepository : JpaRepository<DraftImageGroupEntity, Long> {
    @Query(
        value = """
            SELECT dig
            FROM DraftImageGroupEntity dig
            WHERE dig.key = :key
            ORDER BY dig.createdAt asc
        """,
    )
    fun findOrderedByKey(
        @Param("key")key: String,
    ): List<DraftImageGroupEntity>
}
