package com.kuby.domain.empresa.model

import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class Empresa (
    val id: String = ObjectId().toString(),
    val nombre: String,
    val nit: String,
    val logo: String,
    val contacto: String
)

