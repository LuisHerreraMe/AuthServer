package com.kuby.domain.rol_permiso.repository

import com.kuby.domain.rol_permiso.model.Permiso

interface PermisoDataSource {
    suspend fun createPermiso(permiso: Permiso): Boolean
    suspend fun getPermisoById(id: String): Permiso?
    suspend fun updatePermiso(id: String): Boolean
    suspend fun deletePermisoById(id: String): Boolean

}