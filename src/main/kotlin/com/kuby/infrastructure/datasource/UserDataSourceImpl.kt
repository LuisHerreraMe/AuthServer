package com.kuby.infrastructure.datasource

import com.kuby.domain.rol_permiso.model.Rol
import com.kuby.domain.user.model.User
import com.kuby.domain.user.model.UserUpdate
import com.kuby.domain.user.repository.UserDataSource
import com.mongodb.client.model.Updates
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.CoroutineDatabase
import java.time.LocalDateTime

class UserDataSourceImpl(
    database: CoroutineDatabase
): UserDataSource {

    private  val users = database.getCollection<User>()

    override suspend fun getUserInfoById(id: String): User? {
        return users.findOne(filter = User::id eq id)
    }

    override suspend fun getUserInfoByEmail(emailAddress: String): User? {
        val regex = "^${Regex.escape(emailAddress)}$".toRegex(setOf(RegexOption.IGNORE_CASE))
        return users.findOne(User::emailAddress.regex(regex))
    }

    override suspend fun saveUserInfo(user: User): Boolean {
        val existingUser = users.findOne(filter = User::emailAddress eq user.emailAddress)
        return if (existingUser == null){
            val result = users.insertOne(document = user)
            result.wasAcknowledged()
        } else{
            false
        }
    }

    override suspend fun deleteUser(userId: String): Boolean {
        return users.deleteOne(filter = User::id eq userId).wasAcknowledged()
    }

    override suspend fun updateUserInfo(
        id: String,
        name: String,
        updatedAt: LocalDateTime,
        profilePhoto: String
    ): Boolean {
        val updates = combine(
            setValue(UserUpdate::name, name),
            setValue(UserUpdate::updatedAt, updatedAt.toString()),
            setValue(UserUpdate::profilePhoto, profilePhoto)
        )
        return users.updateOne(
            filter = User::id eq id,
            update = updates
        ).wasAcknowledged()
    }

    override suspend fun addPermission(rolId: String, permisoId: String): Boolean {
        val update = Updates.addToSet(Rol::permisos.toString(), permisoId)
        val result = users.updateOne(
            filter = Rol::id eq rolId,
            update = update
        )
        return result.modifiedCount > 0
    }

    override suspend fun removePermission(rolId: String, permisoId: String): Boolean {
        val update = Updates.pull(Rol::permisos.toString(), permisoId)
        val result = users.updateOne(
            filter = Rol::id eq rolId,
            update = update
        )
        return result.modifiedCount > 0
    }

    override suspend fun updatePermissions(rolId: String, NewPermissions: List<String>): Boolean {
        val update = setValue(Rol::permisos, NewPermissions)
        val result = users.updateOne(
            filter = Rol::id eq rolId,
            update = update
        )
        return result.modifiedCount > 0
    }
}