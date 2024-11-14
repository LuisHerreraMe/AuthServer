package com.kuby.domain.sucursal.model

import com.kuby.util.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import java.time.LocalDateTime
import java.util.*

@Serializable
data class Sucursal(
    val id: String = ObjectId().toString(),
    val idEmpresa: String,
    val nombre: String,
    val phone: String,
    val direccion: String,
    val municipio: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime?= null,
    @Serializable(with = LocalDateTimeSerializer::class)
    val updatedAt: LocalDateTime?= null,
    )