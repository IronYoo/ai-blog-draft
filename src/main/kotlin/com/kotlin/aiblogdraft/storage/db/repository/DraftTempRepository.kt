package com.kotlin.aiblogdraft.storage.db.repository

import com.kotlin.aiblogdraft.storage.db.entity.DraftTempEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface DraftTempRepository : JpaRepository<DraftTempEntity, Long> {
    @Query(
        """
        SELECT dt
        FROM DraftTempEntity dt
        JOIN DraftImageGroupEntity dig ON dt.id = dig.draftTempId
        JOIN DraftImageEntity di ON di.draftImageGroupId = dig.id
        WHERE di.id = :imageId
    """,
    )
    fun findImageUser(
        @Param("imageId") imageId: Long,
    ): DraftTempEntity?
}
