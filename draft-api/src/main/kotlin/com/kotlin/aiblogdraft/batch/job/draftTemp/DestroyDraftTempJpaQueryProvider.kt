package com.kotlin.aiblogdraft.batch.job.draftTemp

import com.kotlin.storage.db.entity.DraftTempEntity
import jakarta.persistence.Query
import jakarta.persistence.TypedQuery
import org.springframework.batch.item.database.orm.AbstractJpaQueryProvider
import java.time.LocalDateTime

class DestroyDraftTempJpaQueryProvider(
    private val targetDateTime: LocalDateTime,
) : AbstractJpaQueryProvider() {
    override fun createQuery(): Query {
        val query: TypedQuery<DraftTempEntity> =
            this.entityManager.createQuery(
                """
                SELECT dt
                FROM DraftTempEntity dt
                WHERE expireAt <= :targetDateTime
                """.trimIndent(),
                DraftTempEntity::class.java,
            )

        query.setParameter("targetDateTime", targetDateTime)

        return query
    }

    override fun afterPropertiesSet() {
        TODO("Not yet implemented")
    }
}
