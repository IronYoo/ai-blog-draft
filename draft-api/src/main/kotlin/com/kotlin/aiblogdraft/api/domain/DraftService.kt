package com.kotlin.aiblogdraft.api.domain

import com.kotlin.aiblogdraft.api.domain.draft.DraftAppender
import com.kotlin.aiblogdraft.api.domain.draft.DraftFinder
import com.kotlin.aiblogdraft.api.domain.draft.DraftReader
import com.kotlin.aiblogdraft.api.domain.draft.dto.AppendDraft
import com.kotlin.aiblogdraft.api.domain.draft.dto.Draft
import com.kotlin.aiblogdraft.api.domain.draft.dto.DraftAppendEvent
import com.kotlin.aiblogdraft.api.domain.draft.dto.DraftStatus
import com.kotlin.aiblogdraft.api.domain.draft.dto.DraftStatusResult
import com.kotlin.aiblogdraft.api.domain.draft.temp.DraftTempAppender
import com.kotlin.aiblogdraft.api.domain.draft.temp.DraftTempFinder
import com.kotlin.aiblogdraft.api.domain.draft.temp.dto.AppendDraftTemp
import com.kotlin.aiblogdraft.api.exception.DraftNotProcessedException
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class DraftService(
    private val draftTempAppender: DraftTempAppender,
    private val draftTempFinder: DraftTempFinder,
    private val draftAppender: DraftAppender,
    private val draftReader: DraftReader,
    private val draftFinder: DraftFinder,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    fun start(userId: Long): Long {
        val tempId = draftTempAppender.append(AppendDraftTemp(userId))

        return tempId
    }

    fun append(
        tempId: Long,
        userId: Long,
        appendDraft: AppendDraft,
    ) {
        draftTempFinder.getValid(tempId, userId)
        val draftId = draftAppender.append(tempId, userId, appendDraft).id
        applicationEventPublisher.publishEvent(DraftAppendEvent(draftId))
    }

    fun status(userId: Long): List<DraftStatusResult> {
        val drafts = draftReader.readAllByUserId(userId)

        return drafts.map { DraftStatusResult(it.id, it.title, DraftStatus.findByStatus(it.status)) }
    }

    fun read(
        id: Long,
        userId: Long,
    ): Draft {
        val found = draftFinder.findDetail(id, userId)
        if (DraftStatus.findByStatus(found.draft.status) != DraftStatus.DONE) throw DraftNotProcessedException()

        return Draft.fromDetail(found)
    }
}
