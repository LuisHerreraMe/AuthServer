package com.kuby.domain.repository

import com.kuby.domain.model.Permiso

interface PermisoDataSource {
    suspend fun createPermiso(permiso: Permiso): Boolean
    suspend fun getPermisoByName(name: String): Permiso?
    suspend fun updatePermisoByName(name: String): Boolean
    suspend fun deletePermisoByName(name: String): Boolean

}