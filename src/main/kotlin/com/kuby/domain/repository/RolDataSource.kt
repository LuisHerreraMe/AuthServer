package com.kuby.domain.repository

import com.kuby.domain.model.Rol

interface RolDataSource {
    suspend fun createRol(rol: Rol): Boolean
    suspend fun getRolByName(name: String): Rol?
    suspend fun updateRolByName(name: String): Boolean
    suspend fun deleteRolByName(name: String): Boolean

}