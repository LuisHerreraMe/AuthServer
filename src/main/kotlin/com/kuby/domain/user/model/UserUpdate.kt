package com.kuby.domain.user.model

import kotlinx.serialization.Serializable

@Serializable
data class UserUpdate(
    val id: String? = null,
    val name: String? = null,
    val lastName: String? = null,
    val phone: String? = null,
    val updatedAt: String?= null,
    val profilePhoto: String? = null
)