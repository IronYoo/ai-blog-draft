package com.kotlin.aiblogdraft.api.domain.draftTemp

import com.kotlin.aiblogdraft.api.exception.DraftTempNotAllowedException
import com.kotlin.aiblogdraft.api.exception.DraftTempNotFoundException
import com.kotlin.storage.db.entity.DraftTempEntity
import com.kotlin.storage.db.repository.DraftTempRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class DraftTempFinder(
    private val draftTempRepository: DraftTempRepository,
) {
    fun getValid(
        id: Long,
        userId: Long,
    ): DraftTempEntity {
        val draftTemp = draftTempRepository.findByIdOrNull(id) ?: throw DraftTempNotFoundException()
        if (draftTemp.userId != userId) throw DraftTempNotAllowedException()

        return draftTemp
    }
}
