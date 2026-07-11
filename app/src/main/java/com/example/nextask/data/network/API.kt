package com.example.nextask.data.network

import io.ktor.client.request.*
import io.ktor.client.call.*
import kotlinx.serialization.Serializable

import com.example.nextask.data.model.LoginRequest
import com.example.nextask.data.model.LoginResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class ApiService {
    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    suspend fun login(request: LoginRequest): LoginResponse {
        // We target the file path location of your PHP server
        val response = client.post("http://10.0.2.2/your_project_folder/login.php") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }

        // Converts the JSON response back to our LoginResponse object
        return response.body()
    }
}