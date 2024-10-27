package com.kuby.domain.empresa.model

import com.kuby.util.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import java.time.LocalDateTime

@Serializable
data class Empresa (
    val id: String = ObjectId().toString(),
    val nombre: String,
    val nit: String,
    val logo: String,
    val contacto: String, @Serializable(with = LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime?= null,
    @Serializable(with = LocalDateTimeSerializer::class)
    val updatedAt: LocalDateTime?= null

    )

