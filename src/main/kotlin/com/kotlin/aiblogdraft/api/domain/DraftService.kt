package com.kotlin.aiblogdraft.api.domain

import com.kotlin.aiblogdraft.api.domain.draft.DraftAppender
import com.kotlin.aiblogdraft.api.domain.draft.DraftFinder
import com.kotlin.aiblogdraft.api.domain.draft.DraftReader
import com.kotlin.aiblogdraft.api.domain.draft.dto.AppendDraft
import com.kotlin.aiblogdraft.api.domain.draft.dto.Draft
import com.kotlin.aiblogdraft.api.domain.draft.dto.DraftStatus
import com.kotlin.aiblogdraft.api.domain.draft.dto.DraftStatusResult
import com.kotlin.aiblogdraft.api.domain.draftTemp.DraftTempAppender
import com.kotlin.aiblogdraft.api.domain.draftTemp.DraftTempFinder
import com.kotlin.aiblogdraft.api.domain.draftTemp.dto.AppendDraftTemp
import org.springframework.stereotype.Service

@Service
class DraftService(
    private val draftTempAppender: DraftTempAppender,
    private val draftTempFinder: DraftTempFinder,
    private val draftAppender: DraftAppender,
    private val draftReader: DraftReader,
    private val draftFinder: DraftFinder,
) {
    fun start(userId: Long): Long {
        val tempId = draftTempAppender.append(AppendDraftTemp(userId))

        return tempId
    }

    fun append(
        tempId: Long,
        userId: Long,
        appendDraft: AppendDraft,
    ): Long {
        draftTempFinder.getValid(tempId, userId)

        return draftAppender.append(tempId, userId, appendDraft).id
    }

    fun status(userId: Long): List<DraftStatusResult> {
        val drafts = draftReader.readAllByUserId(userId)

        return drafts.map { DraftStatusResult(it.id, it.title, DraftStatus.findByStatus(it.status)) }
    }

    fun read(
        id: Long,
        userId: Long,
    ): Draft {
        val draftWithImageGroups = draftFinder.findByIdWithImageGroups(id, userId)

        return Draft.fromDraftWithImageGroups(draftWithImageGroups)
    }
}
