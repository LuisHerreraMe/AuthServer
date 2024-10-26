package com.kuby.domain.user.model

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val name: String,
    val emailAddress: String,
    val password: String,
    val phone: String,
    val profilePhoto: String
)