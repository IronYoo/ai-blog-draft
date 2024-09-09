package com.kotlin.aiblogdraft.api.controller.v1.response

import com.kotlin.aiblogdraft.api.domain.draftImage.AppendImageResult

data class PostDraftImageResponse(
    val id: Long,
    val url: String,
)

fun AppendImageResult.toResponse() =
    PostDraftImageResponse(
        id = id,
        url = url,
    )
