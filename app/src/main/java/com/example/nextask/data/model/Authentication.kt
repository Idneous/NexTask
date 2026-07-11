package com.example.nextask.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val success: Boolean,

    @SerialName("user_id")
    val userId: Int? = null,

    val name: String? = null,
    val message: String? = null
)