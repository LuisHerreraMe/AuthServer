package com.kuby.domain.empresa.model

import com.kuby.util.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class UpdateEmpresa(
    val nombre: String,
    val nit: String,
    val logo: String,
    val emailAddress: String,
    val phone: String,
    val updatedAt: String?= null

    )

