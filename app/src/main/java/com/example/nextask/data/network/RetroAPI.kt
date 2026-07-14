package com.example.nextask.data.network

import com.example.nextask.data.model.CreateTaskRequest
import com.example.nextask.data.model.CreateTaskResponse
import com.example.nextask.data.model.PriorityTask
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body


interface TaskApiService {
    @POST("create_task.php")
    suspend fun createTask(
        @Body request: CreateTaskRequest
    ): Response<CreateTaskResponse>
}