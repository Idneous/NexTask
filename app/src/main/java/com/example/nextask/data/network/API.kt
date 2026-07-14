package com.example.nextask.data.network

import com.example.nextask.data.model.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class ApiService {
    private val baseUrl = "http://10.0.2.2/IT140P/REST"
    
    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    suspend fun login(request: LoginRequest): LoginResponse {
        val response = client.post("$baseUrl/login.php") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return response.body()
    }

    suspend fun register(request: RegisterRequest): RegisterResponse {
        val response = client.post("$baseUrl/register.php") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return response.body()
    }

    suspend fun createTask(request: CreateTaskRequest): CreateTaskResponse {
        val response = client.post("$baseUrl/create_task.php") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return response.body()
    }

    suspend fun getPrioritizedTasks(userId: Int): List<PriorityTask> {
        val response = client.get("$baseUrl/get_prioritized_tasks.php") {
            parameter("user_id", userId)
        }
        return response.body()
    }

    suspend fun updateTaskProgress(taskId: Int, progress: Float): GenericResponse {
        val request = UpdateProgressRequest(taskId, progress)
        val response = client.post("$baseUrl/update_task_progress.php") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return response.body()
    }

    suspend fun getProductivityAnalytics(userId: Int): ProductivityAnalyticsResponse {
        val response = client.get("$baseUrl/get_productivity_analytics.php") {
            parameter("user_id", userId)
        }
        return response.body()
    }

    suspend fun deleteTask(taskId: Int): GenericResponse {
        val request = DeleteTaskRequest(taskId)
        val response = client.post("$baseUrl/delete_task.php") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return response.body()
    }
}
