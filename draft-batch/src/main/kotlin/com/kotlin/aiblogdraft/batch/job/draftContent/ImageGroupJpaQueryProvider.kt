package com.kotlin.aiblogdraft.batch.job.draftContent

import com.kotlin.aiblogdraft.storage.db.entity.DraftImageGroupEntity
import jakarta.persistence.Query
import jakarta.persistence.TypedQuery
import org.springframework.batch.item.database.orm.AbstractJpaQueryProvider

class ImageGroupJpaQueryProvider(
    private val draftId: Long,
) : AbstractJpaQueryProvider() {
    override fun createQuery(): Query {
        val query: TypedQuery<DraftImageGroupEntity> =
            this.entityManager.createQuery(
                """
                SELECT dig
                FROM DraftImageGroupEntity dig
                JOIN FETCH dig.draft d
                WHERE d.id = :draftId
                """.trimIndent(),
                DraftImageGroupEntity::class.java,
            )

        query.setParameter("draftId", draftId)

        return query
    }

    override fun afterPropertiesSet() {
        TODO("Not yet implemented")
    }
}
