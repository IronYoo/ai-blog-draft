package com.kotlin.aiblogdraft.storage.db.entity

import jakarta.persistence.Column
import jakarta.persistence.ConstraintMode
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "draft_image_group")
class DraftImageGroupEntity(
    draftTempId: Long,
) : BaseEntity() {
    @Column(name = "draft_id")
    var draftId: Long? = null
        protected set

    var draftTempId: Long? = draftTempId
        protected set

    fun updateDraft(draftId: Long) {
        this.draftId = draftId
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "draft_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT), insertable = false, updatable = false)
    var draft: DraftEntity? = null

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "draft_image_group_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT), insertable = false, updatable = false)
    var images: MutableList<DraftImageEntity> = mutableListOf()
}
