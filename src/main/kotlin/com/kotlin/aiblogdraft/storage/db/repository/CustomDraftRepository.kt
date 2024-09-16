package com.kotlin.aiblogdraft.storage.db.repository

import com.kotlin.aiblogdraft.storage.db.entity.DraftEntity
import com.kotlin.aiblogdraft.storage.db.entity.QDraftEntity.draftEntity
import com.kotlin.aiblogdraft.storage.db.entity.QDraftImageEntity.draftImageEntity
import com.kotlin.aiblogdraft.storage.db.entity.QDraftImageGroupEntity.draftImageGroupEntity
import com.kotlin.aiblogdraft.storage.db.repository.dto.DraftImageGroup
import com.kotlin.aiblogdraft.storage.db.repository.dto.DraftWithImageGroups
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

interface CustomDraftRepository {
    fun findByIdWithImageGroups(id: Long): DraftWithImageGroups?
}

class CustomDraftRepositoryImpl :
    QuerydslRepositorySupport(DraftEntity::class.java),
    CustomDraftRepository {
    override fun findByIdWithImageGroups(id: Long): DraftWithImageGroups? {
        val result =
            from(draftEntity)
                .select(draftEntity, draftImageEntity, draftImageGroupEntity)
                .join(draftImageGroupEntity)
                .on(draftEntity.key.eq(draftImageGroupEntity.key))
                .join(draftImageEntity)
                .on(draftImageEntity.draftImageGroupId.eq(draftImageGroupEntity.id))
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
                )
            }

        return DraftWithImageGroups(draft, groups)
    }
}
