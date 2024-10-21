package com.kuby.domain.model

import com.kuby.util.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import java.time.LocalDateTime

@Serializable
data class Rol(
    val id: String = ObjectId().toString(),
    val nombre: String,
    val descripcion: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime?= null,
    @Serializable(with = LocalDateTimeSerializer::class)
    val updatedAt: LocalDateTime?= null,
    val permisos: List<String> = listOf()
)