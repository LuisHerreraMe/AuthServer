package com.kuby.domain.user.model

import com.kuby.util.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class User(
    val id: String? = null,
    val name: String? = null,
    val lastName: String? = null,
    val phone: String? = null,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime?= null,
    @Serializable(with = LocalDateTimeSerializer::class)
    val updatedAt: LocalDateTime?= null,
    val emailAddress: String? = null,
    val password: String? = null,
    val profilePhoto: String? = null,
    val idRol: List<String> = listOf()
)
