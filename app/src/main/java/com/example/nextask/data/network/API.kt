package com.example.nextask.data.network

import io.ktor.client.request.*
import io.ktor.client.call.*

import com.example.nextask.data.model.LoginRequest
import com.example.nextask.data.model.LoginResponse
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

//retrofit
import com.example.nextask.ui.homepage.PriorityTask
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body

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
        // where phpfile is located
        val response = client.post("http://10.0.2.2/IT140P/REST/login.php") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }

        // json -> needed response
        return response.body()
    }
}