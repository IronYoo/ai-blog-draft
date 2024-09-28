package com.kotlin.aiblogdraft.storage.db.repository

import com.kotlin.aiblogdraft.storage.db.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByEmail(email: String): UserEntity?
}
