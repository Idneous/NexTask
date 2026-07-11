package com.example.nextask.data.network

import io.ktor.client.request.*
import io.ktor.client.call.*
import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(val id: Int, val name: String, val email: String)

class ApiService {
    suspend fun getUser(): UserProfile {
        // allow ktor to parse JSON
        return ktorClient.get("https://api.example.com/user").body()
    }
}
