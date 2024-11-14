package com.kuby.domain.sucursal.model

import com.kuby.util.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import java.time.LocalDateTime
import java.util.*

@Serializable
data class UpdateSucursal(
    val nombre: String,
    val phone: String,
    val direccion: String,
    val municipio: String,
    val departamento: String,
    val updatedAt: String,
    )
