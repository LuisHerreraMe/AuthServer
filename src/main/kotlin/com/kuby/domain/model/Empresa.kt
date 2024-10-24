package com.kuby.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Empresa (
    val id: String,
    val nombre: String,
    val nit: String,
    val logo: String,
    val contacto: String
)

