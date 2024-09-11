package com.kotlin.aiblogdraft.api.domain

import com.kotlin.aiblogdraft.api.domain.draft.AppendDraft
import com.kotlin.aiblogdraft.api.domain.draft.DraftAppender
import com.kotlin.aiblogdraft.api.domain.draft.DraftReader
import com.kotlin.aiblogdraft.api.domain.draft.DraftStatus
import com.kotlin.aiblogdraft.api.domain.draftImage.AppendImageResult
import com.kotlin.aiblogdraft.api.domain.draftImage.DraftImageSaver
import com.kotlin.aiblogdraft.api.domain.draftKey.AppendDraftKey
import com.kotlin.aiblogdraft.api.domain.draftKey.DraftKeyAppender
import com.kotlin.aiblogdraft.api.domain.draftKey.DraftKeyFinder
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class DraftService(
    private val draftKeyAppender: DraftKeyAppender,
    private val draftKeyFinder: DraftKeyFinder,
    private val draftImageSaver: DraftImageSaver,
    private val draftAppender: DraftAppender,
    private val draftReader: DraftReader,
) {
    fun createKey(userId: Long): String {
        val draftKey = draftKeyAppender.appendKey(AppendDraftKey(userId))
        return draftKey
    }

    fun saveImages(
        key: String,
        files: Array<MultipartFile>,
        userId: Long,
    ): List<AppendImageResult> {
        val draftKey = draftKeyFinder.getValidDraftKey(key, userId).key
        val result = draftImageSaver.save(draftKey, files)
        return result
    }

    fun append(appendDraft: AppendDraft): Long {
        draftKeyFinder.getValidDraftKey(appendDraft.key, appendDraft.userId).key
        return draftAppender.append(appendDraft).id
    }

    fun status(userId: Long): List<DraftStatus> {
        val drafts = draftReader.readByUserId(userId)
        return drafts.map { DraftStatus(it.id, it.title, it.status.name) }
    }
}
