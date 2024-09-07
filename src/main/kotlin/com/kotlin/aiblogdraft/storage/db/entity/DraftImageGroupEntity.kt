package com.kotlin.aiblogdraft.storage.db.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "draft_image_group")
class DraftImageGroupEntity(
    key: String,
) : BaseEntity() {
    @Column(name = "\"key\"")
    var key: String = key
        protected set
}
