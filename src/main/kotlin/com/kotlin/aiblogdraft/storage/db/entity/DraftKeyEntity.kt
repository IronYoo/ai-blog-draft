package com.kotlin.aiblogdraft.storage.db.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity
@Table(
    name = "draft_key",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["user_id", "\"key\""]),
    ],
)
class DraftKeyEntity(
    key: String,
    userId: Long,
) : BaseEntity() {
    @Column(name = "\"key\"")
    var key: String = key
        protected set

    var userId: Long = userId
        protected set
}
