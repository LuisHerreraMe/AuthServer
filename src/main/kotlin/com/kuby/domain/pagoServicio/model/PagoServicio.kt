package com.kuby.domain.pagoServicio.model

import com.kuby.util.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import java.time.LocalDateTime
import java.util.*

@Serializable
data class PagoServicio(
    val id: String,
    val idSede: String,
    val voucher: String,
    val valor: Float,
    val fechaRegistro: String
    )
