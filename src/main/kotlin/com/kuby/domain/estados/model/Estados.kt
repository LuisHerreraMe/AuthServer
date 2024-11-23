package com.kuby.domain.estados.model

import com.kuby.util.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import java.time.LocalDateTime
import java.util.*

@Serializable
data class Estados(
    val id: String = ObjectId().toString(),
    val nombre: String,
    val codigo: String
    )
