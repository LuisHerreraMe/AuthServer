package com.kuby.domain.rol_permiso.repository

import com.kuby.domain.rol_permiso.model.Rol

interface RolDataSource {
    suspend fun createRol(rol: Rol): Boolean
    suspend fun getRolByName(name: String): Rol?
    suspend fun updateRolByName(name: String): Boolean
    suspend fun deleteRolByName(name: String): Boolean
    suspend fun addPermission(rolId: String, permisoId: String): Boolean
    suspend fun removePermission(rolId: String, permisoId: String): Boolean
    suspend fun updatePermissions(rolId: String, NewPermissions: List<String>): Boolean

}