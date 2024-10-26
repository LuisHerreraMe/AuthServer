package com.kuby.domain.model

import com.kuby.domain.user.model.User
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val user: User? = null,
    val token: String? = null
)

@Serializable
data class ApiResponseError(
    val statusCode: Int = 500,
    val message: String = ""
)

