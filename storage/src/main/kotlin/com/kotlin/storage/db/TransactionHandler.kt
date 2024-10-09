package com.kotlin.storage.db

import jakarta.persistence.EntityManager
import jakarta.persistence.EntityManagerFactory
import org.springframework.stereotype.Component

@Component
class TransactionHandler(
    private val entityManagerFactory: EntityManagerFactory,
) {
    fun <T> executeTransaction(action: (EntityManager) -> T): T {
        val entityManager = entityManagerFactory.createEntityManager()
        val transaction = entityManager.transaction
        return try {
            transaction.begin()
            val result = action(entityManager)
            transaction.commit()
            result
        } catch (e: Exception) {
            transaction.rollback()
            throw e
        } finally {
            entityManager.close()
        }
    }
}
