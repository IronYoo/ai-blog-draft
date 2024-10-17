package com.kotlin.aiblogdraft.storage.db.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.springframework.util.MimeType
import org.springframework.util.MimeTypeUtils

@Entity
@Table(name = "draft_image")
class DraftImageEntity(
    cdnUrl: String,
    draftImageGroupId: Long,
    type: MimeType? = null,
    originUrl: String,
    name: String,
) : BaseEntity() {
    var cdnUrl: String = cdnUrl
        protected set

    @Column(name = "draft_image_group_id")
    var draftImageGroupId: Long = draftImageGroupId
        protected set

    var imgType = type ?: MimeTypeUtils.IMAGE_JPEG
        protected set

    var originUrl: String? = originUrl
        protected set

    var name: String = name
        protected set

    var resizedUrl: String? = null
        protected set

    fun resize(url: String) {
        this.resizedUrl = url
    }
}
