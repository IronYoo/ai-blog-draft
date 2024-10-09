package com.kotlin.aiblogdraft.api.domain.draftImage

import com.kotlin.aiblogdraft.api.exception.DraftImageNotFoundException
import com.kotlin.storage.db.repository.DraftImageRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class DraftImageReader(
    private val draftImageRepository: DraftImageRepository,
) {
    fun read(id: Long) = draftImageRepository.findByIdOrNull(id) ?: throw DraftImageNotFoundException()
}
