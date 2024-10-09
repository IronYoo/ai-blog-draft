package com.kotlin.aiblogdraft.storage.db.repository

import com.kotlin.aiblogdraft.storage.db.entity.DraftEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface DraftRepository :
    JpaRepository<DraftEntity, Long>,
    com.kotlin.aiblogdraft.storage.db.repository.CustomDraftRepository {
    fun findByUserIdOrderByCreatedAtDesc(userId: Long): List<DraftEntity>

    @Query(
        """
        SELECT d
        FROM DraftEntity d
        JOIN DraftImageGroupEntity dig ON dig.draftId = d.id
        JOIN DraftImageEntity di ON di.draftImageGroupId = dig.id
        WHERE di.id = :imageId
    """,
    )
    fun findByDraftImage(
        @Param("imageId") imageId: Long,
    ): DraftEntity?
}
