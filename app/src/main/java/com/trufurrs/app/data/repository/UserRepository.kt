package com.trufurrs.app.data.repository

import com.trufurrs.app.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun createUser(user: User): Result<String>
    suspend fun getUserById(userId: String): Result<User?>
    suspend fun updateUser(user: User): Result<Unit>
    suspend fun updateUserTier(userId: String, tier: String): Result<Unit>
    suspend fun completeUserProfile(userId: String, updates: Map<String, Any>): Result<Unit>
    fun observeUser(userId: String): Flow<User?>
}