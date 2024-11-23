package com.kuby.domain.region.model

import com.kuby.util.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import java.time.LocalDateTime
import java.util.*

@Serializable
data class Ciudad(
    val id: String,
    val idRegion: String,
    val nombre: String,
    )
