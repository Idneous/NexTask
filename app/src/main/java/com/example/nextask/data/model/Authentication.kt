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

@Serializable
data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)

@Serializable
data class RegisterResponse(
    val success: Boolean,
    val message: String? = null,
    @SerialName("user_id")
    val userId: String? = null
)
