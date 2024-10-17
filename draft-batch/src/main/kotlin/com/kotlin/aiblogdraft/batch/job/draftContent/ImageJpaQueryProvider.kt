package com.kotlin.aiblogdraft.batch.job.draftContent

import com.kotlin.aiblogdraft.storage.db.entity.DraftImageEntity
import jakarta.persistence.Query
import jakarta.persistence.TypedQuery
import org.springframework.batch.item.database.orm.AbstractJpaQueryProvider

class ImageJpaQueryProvider(
    private val draftId: Long,
) : AbstractJpaQueryProvider() {
    override fun createQuery(): Query {
        val query: TypedQuery<DraftImageEntity> =
            this.entityManager.createQuery(
                """
                SELECT di
                FROM DraftImageEntity di
                JOIN DraftImageGroupEntity dig ON dig.id = di.draftImageGroupId
                JOIN DraftEntity d ON d.id = dig.draftId
                WHERE d.id = :draftId
                """.trimIndent(),
                DraftImageEntity::class.java,
            )

        query.setParameter("draftId", draftId)

        return query
    }

    override fun afterPropertiesSet() {
        TODO("Not yet implemented")
    }
}
