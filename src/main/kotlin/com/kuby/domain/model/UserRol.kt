package com.kuby.domain.model

import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class UserRol(
    val id: String = ObjectId().toString(),
    val idUser: User,
    val idRol: String,
)