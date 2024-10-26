package com.kuby.domain.user.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest (
    val emailAddress: String,
    val password: String,
)