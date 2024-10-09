package com.kotlin.storage.db.entity

import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "`user`")
class UserEntity(
    username: String,
    password: String,
    email: String,
) : BaseEntity() {
    var email: String = email
        protected set

    var username = username
        protected set

    var password = password
        protected set

    fun updatePassword(password: String) {
        this.password = password
    }
}
