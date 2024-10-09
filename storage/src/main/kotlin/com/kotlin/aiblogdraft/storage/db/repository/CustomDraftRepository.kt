package com.kotlin.aiblogdraft.storage.db.repository

import com.kotlin.aiblogdraft.storage.db.entity.DraftEntity
import com.kotlin.aiblogdraft.storage.db.entity.QDraftContentEntity.draftContentEntity
import com.kotlin.aiblogdraft.storage.db.entity.QDraftEntity.draftEntity
import com.kotlin.aiblogdraft.storage.db.entity.QDraftImageEntity.draftImageEntity
import com.kotlin.aiblogdraft.storage.db.entity.QDraftImageGroupEntity.draftImageGroupEntity
import com.kotlin.aiblogdraft.storage.db.repository.dto.DraftImageGroup
import com.kotlin.aiblogdraft.storage.db.repository.dto.FindWithRelationsResult
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

interface CustomDraftRepository {
    fun findWithRelations(id: Long): FindWithRelationsResult?
}

class CustomDraftRepositoryImpl :
    QuerydslRepositorySupport(DraftEntity::class.java),
    com.kotlin.aiblogdraft.storage.db.repository.CustomDraftRepository {
    override fun findWithRelations(id: Long): FindWithRelationsResult? {
        val result =
            from(draftEntity)
                .select(draftEntity, draftImageEntity, draftImageGroupEntity, draftContentEntity)
                .join(draftImageGroupEntity)
                .on(draftEntity.id.eq(draftImageGroupEntity.draftId))
                .join(draftImageEntity)
                .on(draftImageEntity.draftImageGroupId.eq(draftImageGroupEntity.id))
                .leftJoin(draftContentEntity)
                .on(draftContentEntity.imageGroupId.eq(draftImageGroupEntity.id))
                .where(draftEntity.id.eq(id))
                .orderBy(draftImageGroupEntity.createdAt.asc())
                .fetch()

        if (result.isEmpty()) return null

        val draft = result[0].get(draftEntity) ?: return null
        val groups =
            result.groupBy { it.get(draftImageGroupEntity) }.map { (group, tuples) ->
                DraftImageGroup(
                    id = group!!.id,
                    images = tuples.mapNotNull { it.get(draftImageEntity) },
                    content = tuples.firstNotNullOfOrNull { it.get(draftContentEntity) }?.content,
                )
            }

        return FindWithRelationsResult(draft, groups)
    }
}
