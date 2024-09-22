package com.kotlin.aiblogdraft.storage.db.entity

import com.kotlin.aiblogdraft.storage.db.enum.DraftEntityStatus
import com.kotlin.aiblogdraft.storage.db.enum.DraftEntityType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table

@Entity
@Table(name = "draft")
class DraftEntity(
    type: DraftEntityType,
    title: String,
    userId: Long,
    regulationText: String? = null,
) : BaseEntity() {
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(10)")
    var type = type
        protected set

    var title = title
        protected set

    var userId = userId
        protected set

    var regulationText = regulationText
        protected set

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255)")
    var status = DraftEntityStatus.PENDING
        protected set
}
