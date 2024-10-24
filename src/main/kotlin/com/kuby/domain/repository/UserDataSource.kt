package com.kuby.domain.repository

import com.kuby.domain.model.User
import java.time.LocalDateTime

interface UserDataSource {
    suspend fun  getUserInfoById(id: String): User?
    suspend fun  getUserInfoByEmail(emailAddress: String): User?
    suspend fun saveUserInfo(user: User): Boolean
    suspend fun deleteUser(userId: String): Boolean
    suspend fun  updateUserInfo(
        id: String,
        name: String,
        updatedAt: LocalDateTime,
        profilePhoto: String
    ): Boolean
    suspend fun addPermission(rolId: String, permisoId: String): Boolean
    suspend fun removePermission(rolId: String, permisoId: String): Boolean
    suspend fun updatePermissions(rolId: String, NewPermissions: List<String>): Boolean
}