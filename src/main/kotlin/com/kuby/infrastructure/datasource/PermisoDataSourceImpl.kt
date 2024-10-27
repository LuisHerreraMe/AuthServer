package com.kuby.infrastructure.datasource

import com.kuby.domain.rol_permiso.model.Permiso
import com.kuby.domain.rol_permiso.repository.PermisoDataSource
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class PermisoDataSourceImpl(
    private val database: CoroutineDatabase
) : PermisoDataSource {

    private val permisosCollection = database.getCollection<Permiso>()

    override suspend fun createPermiso(permiso: Permiso): Boolean {
        val existingPermiso = permisosCollection.findOne(Permiso::nombre eq permiso.nombre)
        return if (existingPermiso == null) {
            permisosCollection.insertOne(permiso).wasAcknowledged()
        } else {
            false
        }
    }

    override suspend fun getPermisoById(id: String): Permiso? {
        return permisosCollection.findOne(Permiso::id eq id)
    }

    override suspend fun updatePermiso(id: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deletePermisoById(id: String): Boolean {
        TODO("Not yet implemented")
    }
}