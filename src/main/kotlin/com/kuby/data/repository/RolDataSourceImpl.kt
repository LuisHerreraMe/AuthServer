package com.kuby.data.repository

import com.kuby.domain.model.Rol
import com.kuby.domain.repository.RolDataSource
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

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
        TODO("Not yet implemented")
    }
}