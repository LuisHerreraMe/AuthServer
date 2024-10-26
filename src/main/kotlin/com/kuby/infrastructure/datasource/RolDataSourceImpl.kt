package com.kuby.infrastructure.datasource

import com.kuby.domain.rol_permiso.model.Rol
import com.kuby.domain.rol_permiso.repository.RolDataSource
import com.mongodb.client.model.Updates
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.setValue

class RolDataSourceImpl(
    private val database: CoroutineDatabase
) : RolDataSource {

    private val rolesCollection = database.getCollection<Rol>()

    override suspend fun createRol(rol: Rol): Boolean {
        val existingRol = rolesCollection.findOne(Rol::nombre eq rol.nombre)
        return if (existingRol == null) {
            rolesCollection.insertOne(rol).wasAcknowledged()
        } else {
            false
        }
    }

    override suspend fun getRolByName(name: String): Rol? {
        return rolesCollection.findOne(Rol::nombre eq name)
    }

    override suspend fun updateRolByName(name: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteRolByName(name: String): Boolean {
        return rolesCollection.deleteOne(filter = Rol::nombre eq name).wasAcknowledged()
    }

    override suspend fun addPermission(rolId: String, permisoId: String): Boolean {
        val update = Updates.addToSet(Rol::permisos.toString(), permisoId)
        val result = rolesCollection.updateOne(
            filter = Rol::id eq rolId,
            update = update
        )
        return result.modifiedCount > 0
    }

    override suspend fun removePermission(rolId: String, permisoId: String): Boolean {
        val update = Updates.pull(Rol::permisos.toString(), permisoId)
        val result = rolesCollection.updateOne(
            filter = Rol::id eq rolId,
            update = update
        )
        return result.modifiedCount > 0
    }

    override suspend fun updatePermissions(rolId: String, NewPermissions: List<String>): Boolean {
        val update = setValue(Rol::permisos, NewPermissions)
        val result = rolesCollection.updateOne(
            filter = Rol::id eq rolId,
            update = update
        )
        return result.modifiedCount > 0
    }
}