package com.example.nextask.data.model

data class CreateTaskRequest(
    val user_id: Int,
    val title: String,
    val subject: String,
    val deadline: String, // Expects "YYYY-MM-DD HH:MM:SS"
    val estimated_hours: Double,
    val priority_weight: Int
)

// based on jsonencoder
data class CreateTaskResponse(
    val success: Boolean,
    val message: String,
    val task_id: String?
)