package com.kotlin.aiblogdraft.api.domain

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class DraftService(
    private val draftKeyAppender: DraftKeyAppender,
    private val draftKeyFinder: DraftKeyFinder,
    private val draftImageAppender: DraftImageAppender,
) {
    fun createDraftKey(userId: Long): String {
        val draftKey = draftKeyAppender.appendKey(AppendDraftKey(userId))
        return draftKey
    }

    fun appendImages(
        key: String,
        files: Array<MultipartFile>,
        userId: Long,
    ): List<AppendImageResult> {
        val draftKey = draftKeyFinder.getValidDraftKey(key, userId).key
        val result = draftImageAppender.appendImages(draftKey, files)
        return result
    }
}
