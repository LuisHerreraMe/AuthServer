package com.kuby.domain.empresa.model

import kotlinx.serialization.Serializable

@Serializable
data class UpdateEmpresa (
    val nombre: String,
    val nit: String,
    val logo: String,
    val contacto: String
)

