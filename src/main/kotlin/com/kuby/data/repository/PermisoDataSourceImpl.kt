package com.kuby.data.repository

import com.kuby.domain.model.Permiso
import com.kuby.domain.repository.PermisoDataSource
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

    override suspend fun getPermisoByName(name: String): Permiso? {
        return permisosCollection.findOne(Permiso::nombre eq name)
    }

    override suspend fun updatePermisoByName(name: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deletePermisoByName(name: String): Boolean {
        TODO("Not yet implemented")
    }
}