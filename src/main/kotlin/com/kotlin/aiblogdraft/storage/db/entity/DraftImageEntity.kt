package com.kotlin.aiblogdraft.storage.db.entity

import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.springframework.util.MimeType
import org.springframework.util.MimeTypeUtils
import java.time.LocalDateTime

@Entity
@Table(name = "draft_image")
class DraftImageEntity(
    url: String,
    draftImageGroupId: Long,
    type: MimeType? = null,
) : BaseEntity() {
    var url: String = url
        protected set

    var draftImageGroupId: Long = draftImageGroupId
        protected set

    var imgType = type ?: MimeTypeUtils.IMAGE_JPEG
        protected set

    var expireAt: LocalDateTime? = null
        protected set
}
